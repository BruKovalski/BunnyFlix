package br.com.bunnyflix.controller;

import br.com.bunnyflix.catalogo.CatalogoEspecies;
import br.com.bunnyflix.catalogo.Especie;
import br.com.bunnyflix.catalogo.GrupoEspecies;
import br.com.bunnyflix.model.Produto;
import br.com.bunnyflix.service.ProdutoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** Páginas institucionais, robots.txt e sitemap.xml. */
@Controller
public class PaginasController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/sobre")
    public String sobre(Model model) {
        model.addAttribute("paginaAtual", "sobre");
        model.addAttribute("tituloPagina", "Sobre a loja | BunnyFlix");
        model.addAttribute("descricaoPagina",
            "Conheça o BunnyFlix, um catálogo online de produtos para cães, gatos, aves, peixes e pequenos animais.");
        return "sobre";
    }

    @GetMapping("/contato")
    public String contato(Model model) {
        model.addAttribute("paginaAtual", "contato");
        model.addAttribute("tituloPagina", "Contato | BunnyFlix");
        model.addAttribute("descricaoPagina", "Canais de contato do BunnyFlix.");
        return "contato";
    }

    @GetMapping(value = "/robots.txt", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> robots(HttpServletRequest request) {
        String base = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString();
        return ResponseEntity.ok("User-agent: *\nDisallow: /login\nDisallow: /h2-console\nSitemap: " + base + "/sitemap.xml\n");
    }

    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> sitemap(HttpServletRequest request) {
        String base = ServletUriComponentsBuilder.fromContextPath(request).build().toUriString();
        StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
            .append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");
        url(xml, base, "/");
        url(xml, base, "/catalogo");
        url(xml, base, "/sobre");
        url(xml, base, "/contato");
        for (GrupoEspecies g : CatalogoEspecies.grupos()) {
            url(xml, base, "/grupo/" + g.getSlug());
        }
        for (Especie e : CatalogoEspecies.todas()) {
            url(xml, base, "/especie/" + e.getSlug());
        }
        for (Produto p : produtoService.listarTodos()) {
            url(xml, base, "/produto/" + p.getId() + "-" + p.getSlug());
        }
        xml.append("</urlset>\n");
        return ResponseEntity.ok(xml.toString());
    }

    private static void url(StringBuilder xml, String base, String caminho) {
        xml.append("  <url><loc>").append(base).append(caminho).append("</loc></url>\n");
    }
}
