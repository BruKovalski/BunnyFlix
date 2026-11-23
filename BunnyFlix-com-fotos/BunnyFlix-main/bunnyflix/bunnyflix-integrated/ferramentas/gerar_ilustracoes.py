#!/usr/bin/env python3
"""Gera ilustrações SVG originais (estilo plano) para o BunnyFlix.
Uso (na pasta bunnyflix-integrated): python3 ferramentas/gerar_ilustracoes.py src/main/resources/static/img/ilustracoes"""
import sys
from pathlib import Path

OUT = Path(sys.argv[1])
(OUT / "especies").mkdir(parents=True, exist_ok=True)
(OUT / "grupos").mkdir(parents=True, exist_ok=True)
(OUT / "categorias").mkdir(parents=True, exist_ok=True)

W, H = 400, 300
INK = "#2b2b2b"


def svg(body, w=W, h=H, extra=""):
    return (f'<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 {w} {h}" width="{w}" height="{h}" '
            f'preserveAspectRatio="xMidYMid slice" role="img" aria-hidden="true"{extra}>{body}</svg>')


def bg(cor, c2=None):
    c2 = c2 or cor
    return (f'<defs><linearGradient id="g" x1="0" y1="0" x2="1" y2="1">'
            f'<stop offset="0" stop-color="{cor}" stop-opacity=".30"/>'
            f'<stop offset="1" stop-color="{c2}" stop-opacity=".55"/></linearGradient></defs>'
            f'<rect width="{W}" height="{H}" fill="#f6efe3"/><rect width="{W}" height="{H}" fill="url(#g)"/>'
            f'<circle cx="60" cy="50" r="70" fill="#fff" opacity=".18"/>'
            f'<circle cx="350" cy="270" r="90" fill="#fff" opacity=".15"/>'
            f'<ellipse cx="200" cy="278" rx="120" ry="14" fill="#000" opacity=".10"/>')


def eye(x, y, r=7):
    return (f'<circle cx="{x}" cy="{y}" r="{r}" fill="{INK}"/>'
            f'<circle cx="{x + r * .35}" cy="{y - r * .35}" r="{r * .35}" fill="#fff"/>')


def whiskers(cx, cy, s=1):
    out = ""
    for dy in (-6, 4):
        out += (f'<path d="M{cx - 14 * s} {cy + dy} L{cx - 58 * s} {cy + dy * 2 - 4}" stroke="{INK}" stroke-width="2" stroke-linecap="round" opacity=".55"/>'
                f'<path d="M{cx + 14 * s} {cy + dy} L{cx + 58 * s} {cy + dy * 2 - 4}" stroke="{INK}" stroke-width="2" stroke-linecap="round" opacity=".55"/>')
    return out


def nose(cx, cy, cor="#e58a9b", w=16):
    return (f'<path d="M{cx - w / 2} {cy} Q{cx} {cy - 6} {cx + w / 2} {cy} Q{cx} {cy + 12} {cx - w / 2} {cy} Z" fill="{cor}"/>'
            f'<path d="M{cx} {cy + 6} v10" stroke="{INK}" stroke-width="2.5" stroke-linecap="round" opacity=".7"/>'
            f'<path d="M{cx - 9} {cy + 18} Q{cx} {cy + 26} {cx} {cy + 16} Q{cx} {cy + 26} {cx + 9} {cy + 18}" fill="none" stroke="{INK}" stroke-width="2.5" stroke-linecap="round" opacity=".7"/>')


# ---------- mamíferos ----------
def mamifero(slug, cor, pelo, orelha, ear="round", cheeks=False, patch=None, belly=None, big_head=False, snout=None):
    b = bg(cor)
    cx, cy = 200, 168
    rx, ry = (92, 82) if not big_head else (100, 88)
    ears = ""
    if ear == "long":  # coelho
        for sx in (-1, 1):
            x = cx + sx * 42
            ears += (f'<ellipse cx="{x}" cy="62" rx="22" ry="62" fill="{pelo}" transform="rotate({sx * 8} {x} 62)"/>'
                     f'<ellipse cx="{x}" cy="64" rx="11" ry="46" fill="{orelha}" transform="rotate({sx * 8} {x} 64)"/>')
    elif ear == "round":
        for sx in (-1, 1):
            x = cx + sx * 62
            ears += f'<circle cx="{x}" cy="102" r="26" fill="{pelo}"/><circle cx="{x}" cy="104" r="15" fill="{orelha}"/>'
    elif ear == "big":
        for sx in (-1, 1):
            x = cx + sx * 74
            ears += f'<circle cx="{x}" cy="96" r="38" fill="{pelo}"/><circle cx="{x}" cy="98" r="26" fill="{orelha}"/>'
    elif ear == "tri":  # gato
        for sx in (-1, 1):
            x = cx + sx * 56
            ears += (f'<path d="M{x - sx * 34} 120 L{x + sx * 6} 52 L{x + sx * 36} 118 Z" fill="{pelo}"/>'
                     f'<path d="M{x - sx * 18} 114 L{x + sx * 5} 74 L{x + sx * 24} 114 Z" fill="{orelha}"/>')
    elif ear == "floppy":  # cão
        for sx in (-1, 1):
            x = cx + sx * 84
            ears += f'<ellipse cx="{x}" cy="150" rx="30" ry="62" fill="{orelha}" transform="rotate({sx * 14} {x} 150)"/>'
    elif ear == "small":
        for sx in (-1, 1):
            x = cx + sx * 58
            ears += f'<circle cx="{x}" cy="100" r="18" fill="{pelo}"/><circle cx="{x}" cy="102" r="10" fill="{orelha}"/>'
    head = f'<ellipse cx="{cx}" cy="{cy}" rx="{rx}" ry="{ry}" fill="{pelo}"/>'
    extras = ""
    if patch:
        extras += f'<ellipse cx="{cx - 38}" cy="{cy - 30}" rx="34" ry="30" fill="{patch}"/>'
    if cheeks:
        extras += (f'<ellipse cx="{cx - 62}" cy="{cy + 20}" rx="30" ry="26" fill="{belly or "#fff"}" opacity=".9"/>'
                   f'<ellipse cx="{cx + 62}" cy="{cy + 20}" rx="30" ry="26" fill="{belly or "#fff"}" opacity=".9"/>')
    sn = snout or "#fff"
    muzzle = f'<ellipse cx="{cx}" cy="{cy + 30}" rx="40" ry="30" fill="{sn}" opacity=".95"/>'
    face = eye(cx - 34, cy - 8) + eye(cx + 34, cy - 8) + nose(cx, cy + 16, "#e58a9b") + whiskers(cx, cy + 22)
    if ear == "tri":
        face = eye(cx - 34, cy - 8, 8) + eye(cx + 34, cy - 8, 8) + nose(cx, cy + 16, "#e58a9b") + whiskers(cx, cy + 24)
    if ear == "floppy":
        face = (eye(cx - 34, cy - 8) + eye(cx + 34, cy - 8) +
                f'<ellipse cx="{cx}" cy="{cy + 14}" rx="15" ry="11" fill="{INK}"/>'
                f'<path d="M{cx} {cy + 24} v12 M{cx - 16} {cy + 38} Q{cx} {cy + 50} {cx + 16} {cy + 38}" fill="none" stroke="{INK}" stroke-width="3" stroke-linecap="round"/>'
                f'<path d="M{cx + 6} {cy + 40} q8 24 -2 28 q-12 -2 -8 -26 Z" fill="#e86f87"/>')
    return svg(b + ears + head + extras + muzzle + face)


MAMI = {
    "coelhos": dict(cor="#c4895b", pelo="#e8d9c6", orelha="#f4b6c0", ear="long"),
    "porquinhos-da-india": dict(cor="#d9a441", pelo="#c98a4a", orelha="#f0b9a0", ear="small", patch="#f6efe3", snout="#f6efe3"),
    "hamsters": dict(cor="#e0813b", pelo="#e8a45e", orelha="#f4b6a0", ear="round", cheeks=True, belly="#fbe9cf", snout="#fbe9cf"),
    "chinchilas": dict(cor="#7a8ba3", pelo="#a3adba", orelha="#e7bcc4", ear="big", snout="#e4e8ee"),
    "gerbils": dict(cor="#9c7b4c", pelo="#b98a52", orelha="#e8a99a", ear="small", snout="#f1dfc0"),
    "ratos-twister": dict(cor="#8d7b9b", pelo="#f3f1ee", orelha="#f2a7b6", ear="big", patch="#6f5a63", snout="#fbe3e6"),
    "camundongos": dict(cor="#8e9aa5", pelo="#b9bfc6", orelha="#f2b3bd", ear="big", snout="#eceff2"),
    "caes": dict(cor="#3f8fbf", pelo="#e9c28f", orelha="#a56f3f", ear="floppy", snout="#f6e3c6"),
    "gatos": dict(cor="#cf6a8a", pelo="#e7945a", orelha="#f4b6a0", ear="tri", snout="#fbe3cf"),
}


# ---------- aves ----------
def ave(cor, corpo, barriga, crista=None, bochecha=None, rosto=None, bico="#f0a030", asa=None):
    b = bg(cor)
    cx, cy = 200, 170
    out = b
    # galho
    out += '<path d="M40 238 Q200 222 360 240" stroke="#8a5a3a" stroke-width="12" fill="none" stroke-linecap="round"/>'
    out += '<path d="M80 236 l-14 -16 M300 232 l16 -18" stroke="#8a5a3a" stroke-width="6" stroke-linecap="round"/>'
    # cauda
    out += f'<path d="M232 200 L300 262 L262 266 L222 220 Z" fill="{asa or corpo}" opacity=".9"/>'
    # corpo
    out += f'<ellipse cx="{cx}" cy="{cy + 12}" rx="74" ry="66" fill="{corpo}"/>'
    out += f'<ellipse cx="{cx - 14}" cy="{cy + 28}" rx="44" ry="44" fill="{barriga}"/>'
    out += f'<path d="M226 150 Q276 170 258 224 Q220 218 214 168 Z" fill="{asa or corpo}" opacity=".85"/>'
    # pés
    out += f'<path d="M184 232 v10 M216 232 v10" stroke="#e0a070" stroke-width="5" stroke-linecap="round"/>'
    # cabeça
    out += f'<circle cx="{cx - 20}" cy="{cy - 56}" r="52" fill="{rosto or corpo}"/>'
    if crista:
        out += (f'<path d="M{cx - 30} {cy - 100} Q{cx - 18} {cy - 150} {cx + 14} {cy - 142} Q{cx - 2} {cy - 126} {cx + 4} {cy - 100} Z" fill="{crista}"/>'
                f'<path d="M{cx - 50} {cy - 96} Q{cx - 56} {cy - 132} {cx - 28} {cy - 128} Q{cx - 38} {cy - 114} {cx - 36} {cy - 96} Z" fill="{crista}"/>')
    if bochecha:
        out += f'<circle cx="{cx - 30}" cy="{cy - 36}" r="14" fill="{bochecha}"/>'
    out += eye(cx - 18, cy - 64, 8)
    out += f'<path d="M{cx - 66} {cy - 56} Q{cx - 92} {cy - 44} {cx - 66} {cy - 28} Q{cx - 56} {cy - 42} {cx - 66} {cy - 56} Z" fill="{bico}"/>'
    return svg(out)


AVES = {
    "calopsitas": dict(cor="#e2c13f", corpo="#b8b8b8", barriga="#d8d8d8", crista="#f6dc4e", bochecha="#f08a3c", rosto="#f6dc4e", asa="#9a9a9a", bico="#8d8d8d"),
    "periquitos-australianos": dict(cor="#43b581", corpo="#58c26a", barriga="#8fdc7a", rosto="#fbe96a", bochecha="#6a7bd8", asa="#2f9b55", bico="#e7b25a"),
    "agapornis": dict(cor="#e0585a", corpo="#4fb25a", barriga="#7ccc66", rosto="#f06a4e", asa="#2f8f45", bico="#f4b183"),
    "canarios": dict(cor="#f0b429", corpo="#f6d23a", barriga="#fbe678", rosto="#f6d23a", asa="#e8b923", bico="#f0a24a"),
}


# ---------- peixes ----------
def peixe(cor, corpo, nadadeira, listra=None, cauda="fan", pintas=False, agua="#8cc8e6", solo=False):
    b = (f'<defs><linearGradient id="a" x1="0" y1="0" x2="0" y2="1"><stop offset="0" stop-color="{agua}" stop-opacity=".55"/>'
         f'<stop offset="1" stop-color="{cor}" stop-opacity=".35"/></linearGradient></defs>'
         f'<rect width="{W}" height="{H}" fill="#e9f3f6"/><rect width="{W}" height="{H}" fill="url(#a)"/>')
    for x, y, r in ((70, 220, 8), (84, 190, 5), (330, 80, 9), (312, 112, 5), (350, 150, 6), (60, 90, 6)):
        b += f'<circle cx="{x}" cy="{y}" r="{r}" fill="none" stroke="#fff" stroke-width="2.5" opacity=".7"/>'
    b += '<path d="M0 282 Q60 262 120 280 T240 276 T400 280 V300 H0Z" fill="#c9b48a" opacity=".6"/>'
    b += '<path d="M330 290 q-10 -50 6 -80 M346 290 q10 -40 -4 -62" stroke="#3a9a6a" stroke-width="7" fill="none" stroke-linecap="round" opacity=".7"/>'
    cx, cy = 190, 150
    out = '' if solo else b
    if cauda == "fan":
        out += f'<path d="M270 150 Q340 90 350 150 Q340 210 270 150 Z" fill="{nadadeira}"/>'
    elif cauda == "long":
        out += f'<path d="M262 150 Q350 60 372 130 Q380 170 340 220 Q300 200 262 150 Z" fill="{nadadeira}"/>'
        out += f'<path d="M200 98 Q250 36 290 70 Q260 100 236 112 Z" fill="{nadadeira}" opacity=".9"/>'
        out += f'<path d="M190 200 Q240 262 290 236 Q250 206 226 190 Z" fill="{nadadeira}" opacity=".9"/>'
    elif cauda == "gold":
        out += f'<path d="M262 150 Q336 70 360 120 Q340 150 360 180 Q336 232 262 150 Z" fill="{nadadeira}"/>'
        out += f'<path d="M170 96 Q200 56 240 92 Z" fill="{nadadeira}"/>'
    out += f'<ellipse cx="{cx}" cy="{cy}" rx="88" ry="54" fill="{corpo}"/>'
    if listra:
        out += f'<path d="M110 {cy - 4} Q190 {cy - 26} 262 {cy - 2}" stroke="{listra[0]}" stroke-width="12" fill="none" stroke-linecap="round"/>'
        out += f'<path d="M190 {cy + 24} Q230 {cy + 22} 262 {cy + 14}" stroke="{listra[1]}" stroke-width="12" fill="none" stroke-linecap="round"/>'
    if pintas:
        for x, y in ((190, 120), (214, 150), (170, 168), (236, 126), (200, 182)):
            out += f'<circle cx="{x}" cy="{y}" r="6" fill="{nadadeira}" opacity=".7"/>'
    out += f'<path d="M168 {cy + 8} Q150 {cy + 52} 196 {cy + 40} Z" fill="{nadadeira}" opacity=".9"/>'
    out += eye(128, cy - 10, 9)
    out += f'<path d="M104 {cy + 14} Q116 {cy + 22} 128 {cy + 16}" stroke="{INK}" stroke-width="2.5" fill="none" stroke-linecap="round"/>'
    return out if solo else svg(out)


PEIXES = {
    "bettas": dict(cor="#7b4fd1", corpo="#5a3fb8", nadadeira="#d6407a", cauda="long"),
    "kinguios": dict(cor="#ef7d2a", corpo="#f58a2e", nadadeira="#f9a24c", cauda="gold", agua="#9fd3e6"),
    "guppies": dict(cor="#2ab7a9", corpo="#8fd3c8", nadadeira="#f06a8f", cauda="fan", pintas=True),
    "neons": dict(cor="#2f7de1", corpo="#c9d6e4", nadadeira="#e6cfd0", listra=("#27b4f2", "#e8403c"), cauda="fan"),
}

# ---------- gravar espécies ----------
for slug, kw in MAMI.items():
    (OUT / "especies" / f"{slug}.svg").write_text(mamifero(slug, **kw), encoding="utf-8")
for slug, kw in AVES.items():
    (OUT / "especies" / f"{slug}.svg").write_text(ave(**kw), encoding="utf-8")
for slug, kw in PEIXES.items():
    (OUT / "especies" / f"{slug}.svg").write_text(peixe(**kw), encoding="utf-8")


# ---------- setores: composição de dois animais ----------
def grupo(slug, cor, partes):
    b = bg(cor)
    out = b
    for (arquivo, x, y, esc) in partes:
        # reusa o desenho da espécie como grupo aninhado, sem fundo
        inner = (OUT / "especies" / arquivo).read_text(encoding="utf-8")
        import re
        corpo = re.sub(r'^<svg[^>]*>', '', inner)
        corpo = re.sub(r'</svg>$', '', corpo)
        # remove fundo (defs + rects/circles de fundo + sombra), mantém desenho do animal
        corpo = corpo.split('<ellipse cx="200" cy="278"')[-1] if 'cx="200" cy="278"' in corpo else corpo
        corpo = corpo.split('/>', 1)[1] if corpo.startswith(' rx=') else corpo
        out += f'<g transform="translate({x} {y}) scale({esc})">{corpo}</g>'
    (OUT / "grupos" / f"{slug}.svg").write_text(svg(out), encoding="utf-8")


grupo("caes-e-gatos", "#3f8fbf", [("caes.svg", -70, 30, .85), ("gatos.svg", 110, 30, .85)])
grupo("pequenos-mamiferos", "#c4895b", [("coelhos.svg", -90, 40, .8), ("hamsters.svg", 125, 70, .72)])
grupo("aves", "#e2c13f", [("periquitos-australianos.svg", -80, 20, .85), ("canarios.svg", 120, 40, .8)])
_b = bg("#2f7de1") + '<path d="M0 282 Q60 262 120 280 T240 276 T400 280 V300 H0Z" fill="#c9b48a" opacity=".6"/>'
_b += ''.join(f'<circle cx="{x}" cy="{y}" r="{r}" fill="none" stroke="#fff" stroke-width="2.5" opacity=".7"/>' for x, y, r in ((70, 220, 8), (330, 80, 9), (350, 150, 6), (60, 90, 6)))
_b += f'<g transform="translate(-70 10) scale(.8)">{peixe(solo=True, **PEIXES["bettas"])}</g>'
_b += f'<g transform="translate(130 110) scale(.6)">{peixe(solo=True, **PEIXES["neons"])}</g>'
(OUT / "grupos" / "peixes.svg").write_text(svg(_b), encoding="utf-8")

# ---------- categorias (fundo transparente, ficam sobre a cor da espécie) ----------
def cat(slug, corpo):
    (OUT / "categorias" / f"{slug}.svg").write_text(
        svg(corpo, extra=' style="padding:0"').replace('preserveAspectRatio="xMidYMid slice"', 'preserveAspectRatio="xMidYMid meet"'),
        encoding="utf-8")


sombra = '<ellipse cx="200" cy="262" rx="110" ry="12" fill="#000" opacity=".12"/>'
cat("alimentos", sombra +
    '<path d="M90 130 H310 L288 250 Q286 262 272 262 H128 Q114 262 112 250 Z" fill="#c8643c"/>'
    '<path d="M90 130 H310" stroke="#a94f2c" stroke-width="10" stroke-linecap="round"/>'
    '<path d="M120 130 Q130 70 160 96 Q176 50 206 90 Q232 54 252 96 Q282 78 282 130 Z" fill="#d9a441"/>'
    '<circle cx="168" cy="108" r="9" fill="#b9822a"/><circle cx="214" cy="104" r="9" fill="#b9822a"/><circle cx="250" cy="114" r="8" fill="#b9822a"/>'
    '<path d="M150 190 q50 24 100 0" stroke="#fff" stroke-width="8" fill="none" stroke-linecap="round" opacity=".8"/>')
cat("gaiolas", sombra +
    '<path d="M110 150 L200 70 L290 150 Z" fill="#a94f2c"/><rect x="124" y="150" width="152" height="108" rx="6" fill="#e9c28f"/>'
    '<path d="M124 150 H276" stroke="#8c5a2b" stroke-width="6"/>'
    '<path d="M172 258 V196 Q200 168 228 196 V258 Z" fill="#6b4226"/>'
    '<circle cx="200" cy="120" r="12" fill="#f6efe3"/><path d="M200 108 v24 M188 120 h24" stroke="#a94f2c" stroke-width="3"/>')
cat("brinquedos", sombra +
    '<circle cx="200" cy="150" r="84" fill="#e0585a"/><path d="M122 120 Q200 168 278 120" stroke="#fff" stroke-width="10" fill="none" opacity=".85"/>'
    '<path d="M126 184 Q200 130 274 184" stroke="#fff" stroke-width="10" fill="none" opacity=".85"/>'
    '<circle cx="168" cy="112" r="14" fill="#fff" opacity=".35"/>')
cat("acessorios", sombra +
    '<path d="M100 200 H300 L280 258 H120 Z" fill="#3f8fbf"/><ellipse cx="200" cy="200" rx="100" ry="22" fill="#6bb4dc"/>'
    '<ellipse cx="200" cy="200" rx="82" ry="14" fill="#2d6a8f"/>'
    '<path d="M110 120 Q200 30 290 120" stroke="#c8643c" stroke-width="14" fill="none" stroke-linecap="round"/>'
    '<circle cx="200" cy="96" r="18" fill="#d9a441"/><circle cx="200" cy="96" r="7" fill="#a97d1f"/>')
cat("higiene", sombra +
    '<rect x="150" y="110" width="100" height="150" rx="22" fill="#43b581"/><rect x="172" y="76" width="56" height="36" rx="8" fill="#2f9b6a"/>'
    '<path d="M200 76 V54 H236 Q250 54 250 68" stroke="#2f9b6a" stroke-width="12" fill="none" stroke-linecap="round"/>'
    '<rect x="166" y="150" width="68" height="62" rx="10" fill="#fff" opacity=".9"/>'
    '<path d="M200 164 q-14 18 0 30 q14 -12 0 -30z" fill="#43b581"/>'
    '<circle cx="104" cy="120" r="16" fill="#9fd3e6" opacity=".7"/><circle cx="84" cy="168" r="10" fill="#9fd3e6" opacity=".7"/><circle cx="306" cy="156" r="14" fill="#9fd3e6" opacity=".7"/>')

# ---------- hero (fundo largo, tons claros sobre o verde-petróleo do banner) ----------
def pata(x, y, s, o):
    return (f'<g transform="translate({x} {y}) scale({s})" fill="#fff" opacity="{o}">'
            '<ellipse cx="0" cy="14" rx="20" ry="17"/><ellipse cx="-24" cy="-6" rx="8" ry="11"/>'
            '<ellipse cx="-8" cy="-20" rx="8" ry="12"/><ellipse cx="10" cy="-20" rx="8" ry="12"/><ellipse cx="26" cy="-6" rx="8" ry="11"/></g>')

hero = ('<defs><linearGradient id="h" x1="0" y1="0" x2="1" y2="1"><stop offset="0" stop-color="#17484a"/><stop offset="1" stop-color="#2a7c74"/></linearGradient></defs>'
        '<rect width="1600" height="600" fill="url(#h)"/>'
        '<circle cx="1330" cy="130" r="260" fill="#fff" opacity=".07"/><circle cx="1500" cy="460" r="200" fill="#c8643c" opacity=".22"/>'
        '<circle cx="1120" cy="520" r="120" fill="#fff" opacity=".06"/>')
for x, y, s, o in ((1180, 120, 1.6, .16), (1340, 300, 2.2, .13), (1480, 120, 1.1, .16), (1220, 440, 1.3, .12), (1000, 250, .9, .10), (1500, 520, 1.7, .12), (880, 90, .8, .08)):
    hero += pata(x, y, s, o)
(OUT / "hero.svg").write_text(svg(hero, 1600, 600), encoding="utf-8")
print("ok")
