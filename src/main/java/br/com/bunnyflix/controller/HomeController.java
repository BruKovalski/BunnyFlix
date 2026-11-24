package br.com.bunnyflix.controller;

import br.com.bunnyflix.catalogo.CatalogoEspecies;
import br.com.bunnyflix.catalogo.Especie;
import br.com.bunnyflix.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class HomeController {

    /** Seleção editorial da loja exibida na página inicial (títulos iguais aos do banco). */
    private static final List<String> TITULOS_DESTAQUE = List.of(
        "Feno Premium de Timóteo",
        "Ração Premium para Cães Adultos",
        "Arranhador Torre com Plataformas",
        "Gaiola Ampla com Poleiros",
        "Aquário Nano com Tampa e LED",
        "Roda Silenciosa Turbo Spinner",
        "Fonte de Água com Filtro",
        "Banheiro de Areia Vulcânica"
    );

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(required = false) String especie,
                        @RequestParam(required = false) String q) {

        // Links antigos (/?especie=Coelhos e /?q=feno) continuam funcionando.
        String busca = q == null ? "" : q.trim();
        if (especie != null && !especie.isBlank()) {
            Especie e = CatalogoEspecies.buscar(especie.trim());
            if (e != null) {
                return "redirect:/especie/" + e.getSlug() + consulta(busca);
            }
        }
        if (!busca.isEmpty()) {
            return "redirect:/catalogo" + consulta(busca);
        }

        model.addAttribute("paginaAtual", "inicio");
        model.addAttribute("tituloPagina", "BunnyFlix – Catálogo de produtos para o seu pet");
        model.addAttribute("descricaoPagina",
            "Catálogo de alimentos, habitats, brinquedos, acessórios e produtos de higiene para cães, gatos, "
                + "aves, peixes, coelhos e roedores.");
        model.addAttribute("destaques", produtoService.selecionar(TITULOS_DESTAQUE));
        return "index";
    }

    private static String consulta(String busca) {
        return busca.isEmpty() ? "" : "?q=" + UriUtils.encodeQueryParam(busca, StandardCharsets.UTF_8);
    }
}
