#!/usr/bin/env python3
"""
Baixa fotos reais e livres do Wikimedia Commons para o BunnyFlix.

Uso (na pasta bunnyflix-integrated, com internet):
    python3 ferramentas/baixar_fotos.py                  # baixa tudo que ainda não existe
    python3 ferramentas/baixar_fotos.py coelhos gatos    # só alguns
    python3 ferramentas/baixar_fotos.py --forcar         # baixa de novo, sobrescrevendo

Só usa Python 3 (biblioteca padrão). Aceita apenas licenças CC0, domínio público, CC BY e CC BY-SA.
Salva em src/main/resources/static/img/ e grava os créditos em CREDITOS-FOTOS.md.
CC BY e CC BY-SA exigem mostrar o crédito no site: use o CREDITOS-FOTOS.md para isso.
CONFIRA cada foto antes de publicar: a busca é automática e pode escolher uma imagem ruim.
"""
import html
import json
import re
import sys
import time
import urllib.error
import urllib.parse
import urllib.request
from pathlib import Path

RAIZ = Path(__file__).resolve().parent.parent
IMG = RAIZ / "src/main/resources/static/img"
API = "https://commons.wikimedia.org/w/api.php"
UA = "BunnyFlix-catalogo/1.0 (projeto escolar; baixar_fotos.py)"

# destino (sem extensão) -> (busca no Commons, largura em px)
ALVOS = {
    "hero": ("dog and cat together", 1600),
    "grupos/caes-e-gatos": ("dog and cat", 800),
    "grupos/pequenos-mamiferos": ("pet hamster guinea pig", 800),
    "grupos/aves": ("pet bird parrot cage", 800),
    "grupos/peixes": ("tropical aquarium fish", 800),
    "especies/coelhos": ("domestic rabbit", 800),
    "especies/porquinhos-da-india": ("guinea pig Cavia porcellus", 800),
    "especies/hamsters": ("Syrian hamster", 800),
    "especies/chinchilas": ("Chinchilla lanigera", 800),
    "especies/gerbils": ("Mongolian gerbil", 800),
    "especies/ratos-twister": ("fancy rat pet", 800),
    "especies/camundongos": ("fancy mouse pet", 800),
    "especies/caes": ("domestic dog portrait", 800),
    "especies/gatos": ("domestic cat portrait", 800),
    "especies/calopsitas": ("cockatiel Nymphicus hollandicus", 800),
    "especies/periquitos-australianos": ("budgerigar Melopsittacus undulatus", 800),
    "especies/agapornis": ("Agapornis roseicollis", 800),
    "especies/canarios": ("domestic canary Serinus canaria", 800),
    "especies/bettas": ("Betta splendens", 800),
    "especies/kinguios": ("goldfish Carassius auratus", 800),
    "especies/guppies": ("guppy Poecilia reticulata", 800),
    "especies/neons": ("neon tetra Paracheirodon innesi", 800),
}

LICENCAS_OK = re.compile(r"^(CC0|Public domain|PD|CC[ -]BY(-SA)?\b)", re.I)


def pedir(url, tentativas=4):
    for i in range(tentativas):
        try:
            req = urllib.request.Request(url, headers={"User-Agent": UA})
            with urllib.request.urlopen(req, timeout=30) as r:
                return r.read()
        except urllib.error.HTTPError as e:
            if e.code in (429, 503) and i < tentativas - 1:
                time.sleep(5 * (i + 1))
                continue
            raise
    raise RuntimeError("falha ao acessar " + url)


def texto_puro(v):
    return html.unescape(re.sub(r"<[^>]+>", "", v or "")).strip()


def buscar(termo, largura):
    params = {
        "action": "query", "format": "json", "generator": "search",
        "gsrsearch": termo + " filetype:bitmap", "gsrnamespace": 6, "gsrlimit": 20,
        "prop": "imageinfo", "iiprop": "url|size|mime|extmetadata", "iiurlwidth": largura,
    }
    dados = json.loads(pedir(API + "?" + urllib.parse.urlencode(params)))
    paginas = sorted(dados.get("query", {}).get("pages", {}).values(), key=lambda p: p.get("index", 0))
    for p in paginas:
        info = (p.get("imageinfo") or [{}])[0]
        meta = info.get("extmetadata", {})
        licenca = texto_puro(meta.get("LicenseShortName", {}).get("value"))
        if info.get("mime") != "image/jpeg" or not LICENCAS_OK.match(licenca):
            continue
        if info.get("width", 0) < largura or info.get("height", 0) < largura * 0.55:
            continue
        if not info.get("thumburl"):
            continue
        return {
            "arquivo": p["title"], "url": info["thumburl"], "pagina": info["descriptionshorturl"],
            "autor": texto_puro(meta.get("Artist", {}).get("value")) or "autor não informado",
            "licenca": licenca,
        }
    return None


def main():
    args = [a for a in sys.argv[1:] if not a.startswith("--")]
    forcar = "--forcar" in sys.argv
    alvos = {k: v for k, v in ALVOS.items() if not args or any(a in k for a in args)}
    creditos, falhas = [], []
    for destino, (termo, largura) in alvos.items():
        caminho = IMG / (destino + ".jpg")
        existentes = list(caminho.parent.glob(caminho.stem + ".*"))
        if existentes and not forcar:
            print("já existe, pulando:", destino)
            continue
        try:
            achado = buscar(termo, largura)
            if not achado:
                raise RuntimeError("nenhuma foto adequada encontrada")
            caminho.parent.mkdir(parents=True, exist_ok=True)
            caminho.write_bytes(pedir(achado["url"]))
            creditos.append((destino, achado))
            print("ok:", destino, "<-", achado["arquivo"], "(" + achado["licenca"] + ")")
        except Exception as e:  # noqa: BLE001
            falhas.append(destino)
            print("FALHOU:", destino, "-", e)
        time.sleep(1)

    if creditos:
        arq = RAIZ / "CREDITOS-FOTOS.md"
        novo = not arq.exists()
        with arq.open("a", encoding="utf-8") as f:
            if novo:
                f.write("# Créditos das fotos\n\nFotos do Wikimedia Commons. Licenças: CC0, domínio público, CC BY e CC BY-SA.\n\n")
            for destino, a in creditos:
                f.write(f"- `{destino}.jpg` – {a['arquivo']} – {a['autor']} – {a['licenca']} – {a['pagina']}\n")
        print("\nCréditos gravados em", arq)
    if falhas:
        print("\nSem foto (continuam com ícone):", ", ".join(falhas))
    print("\nConfira as imagens em", IMG, "e reinicie a aplicação.")


if __name__ == "__main__":
    main()
