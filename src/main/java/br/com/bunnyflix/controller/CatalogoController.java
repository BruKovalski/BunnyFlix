package br.com.bunnyflix.controller;

import br.com.bunnyflix.catalogo.CatalogoCategorias;
import br.com.bunnyflix.catalogo.CatalogoEspecies;
import br.com.bunnyflix.catalogo.CategoriaProduto;
import br.com.bunnyflix.catalogo.Especie;
import br.com.bunnyflix.catalogo.GrupoEspecies;
import br.com.bunnyflix.catalogo.ResultadoCatalogo;
import br.com.bunnyflix.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

/** Listagens do catálogo: geral, por grupo (setor) e por espécie, com busca, filtros e paginação. */
@Controller
public class CatalogoController {

    private static final Set<String> ORDENS = Set.of("nome", "preco-asc", "preco-desc");

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/catalogo")
    public String catalogo(Model model,
                           @RequestParam(required = false) String q,
                           @RequestParam(required = false) String categoria,
                           @RequestParam(required = false) String min,
                           @RequestParam(required = false) String max,
                           @RequestParam(required = false) String ordem,
                           @RequestParam(defaultValue = "1") int pagina) {
        return listar(model, null, null, "/catalogo", q, categoria, min, max, ordem, pagina);
    }

    @GetMapping("/grupo/{slug}")
    public String grupo(Model model, @PathVariable String slug,
                        @RequestParam(required = false) String q,
                        @RequestParam(required = false) String categoria,
                        @RequestParam(required = false) String min,
                        @RequestParam(required = false) String max,
                        @RequestParam(required = false) String ordem,
                        @RequestParam(defaultValue = "1") int pagina) {
        GrupoEspecies grupo = CatalogoEspecies.grupoPorSlug(slug);
        if (grupo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return listar(model, grupo, null, "/grupo/" + grupo.getSlug(), q, categoria, min, max, ordem, pagina);
    }

    @GetMapping("/especie/{slug}")
    public String especie(Model model, @PathVariable String slug,
                          @RequestParam(required = false) String q,
                          @RequestParam(required = false) String categoria,
                          @RequestParam(required = false) String min,
                          @RequestParam(required = false) String max,
                          @RequestParam(required = false) String ordem,
                          @RequestParam(defaultValue = "1") int pagina) {
        Especie especie = CatalogoEspecies.buscarPorSlug(slug);
        if (especie == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return listar(model, especie.getGrupo(), especie, "/especie/" + especie.getSlug(),
            q, categoria, min, max, ordem, pagina);
    }

    private String listar(Model model, GrupoEspecies grupo, Especie especie, String baseUrl,
                          String q, String categoriaSlug, String min, String max, String ordem, int pagina) {

        String busca = limpar(q);
        CategoriaProduto categoria = CatalogoCategorias.buscarPorSlug(categoriaSlug);
        Double precoMin = lerPreco(min);
        Double precoMax = lerPreco(max);
        String ordemValida = ordem != null && ORDENS.contains(ordem) ? ordem : null;

        ResultadoCatalogo resultado = produtoService.pesquisar(
            grupo != null ? grupo.getNome() : null,
            especie != null ? especie.getNome() : null,
            categoria != null ? categoria.getValor() : null,
            busca, precoMin, precoMax, ordemValida, pagina);

        String minTxt = precoMin != null ? min.trim() : null;
        String maxTxt = precoMax != null ? max.trim() : null;
        boolean filtrado = busca != null || categoria != null || precoMin != null
            || precoMax != null || ordemValida != null;

        model.addAttribute("paginaAtual", grupo != null ? "grupo:" + grupo.getSlug() : "catalogo");
        model.addAttribute("grupoInfo", grupo);
        model.addAttribute("especieInfo", especie);
        model.addAttribute("especiesDoGrupo", grupo != null ? CatalogoEspecies.doGrupo(grupo) : List.of());
        model.addAttribute("resultado", resultado);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("queryBase", montarQuery(busca, categoria, minTxt, maxTxt, ordemValida));
        model.addAttribute("busca", busca);
        model.addAttribute("categoriaSel", categoria != null ? categoria.getSlug() : null);
        model.addAttribute("minTxt", minTxt);
        model.addAttribute("maxTxt", maxTxt);
        model.addAttribute("ordem", ordemValida);
        model.addAttribute("filtrado", filtrado);
        model.addAttribute("noindex", busca != null);
        model.addAttribute("tituloPagina", montarTitulo(grupo, especie, categoria, busca, resultado.getPagina()));
        model.addAttribute("descricaoPagina", montarDescricao(grupo, especie));
        return "catalogo";
    }

    private static String limpar(String valor) {
        if (valor == null) {
            return null;
        }
        String limpo = valor.trim();
        return limpo.isEmpty() ? null : limpo;
    }

    /** Aceita "12,50" ou "12.50". Valores inválidos ou negativos são ignorados. */
    private static Double lerPreco(String texto) {
        if (texto == null || texto.isBlank()) {
            return null;
        }
        try {
            double valor = Double.parseDouble(texto.trim().replace(',', '.'));
            return Double.isFinite(valor) && valor >= 0 ? valor : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /** Monta "?q=...&amp;categoria=...&amp;" para os links de paginação (termina em "?" ou "&"). */
    private static String montarQuery(String busca, CategoriaProduto categoria, String min, String max, String ordem) {
        StringBuilder sb = new StringBuilder("?");
        adicionar(sb, "q", busca);
        adicionar(sb, "categoria", categoria != null ? categoria.getSlug() : null);
        adicionar(sb, "min", min);
        adicionar(sb, "max", max);
        adicionar(sb, "ordem", ordem);
        return sb.toString();
    }

    private static void adicionar(StringBuilder sb, String nome, String valor) {
        if (valor != null) {
            sb.append(nome).append('=').append(UriUtils.encodeQueryParam(valor, StandardCharsets.UTF_8)).append('&');
        }
    }

    private static String montarTitulo(GrupoEspecies grupo, Especie especie, CategoriaProduto categoria,
                                       String busca, int pagina) {
        String base;
        if (busca != null) {
            base = "Busca por “" + busca + "”";
        } else if (especie != null) {
            base = "Produtos para " + especie.getNome();
        } else if (grupo != null) {
            base = grupo.getNome();
        } else {
            base = "Catálogo de produtos";
        }
        if (categoria != null) {
            base += " – " + categoria.getRotulo();
        }
        if (pagina > 1) {
            base += " (página " + pagina + ")";
        }
        return base + " | BunnyFlix";
    }

    private static String montarDescricao(GrupoEspecies grupo, Especie especie) {
        if (especie != null) {
            return "Produtos para " + especie.getNome() + " no catálogo BunnyFlix: alimentação, habitats, "
                + "brinquedos, acessórios e higiene.";
        }
        if (grupo != null) {
            return grupo.getDescricao() + " Consulte o catálogo BunnyFlix.";
        }
        return "Consulte todos os produtos do catálogo BunnyFlix por espécie, tipo de produto e faixa de preço.";
    }
}
