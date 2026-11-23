package br.com.bunnyflix.controller;

import br.com.bunnyflix.catalogo.CatalogoCategorias;
import br.com.bunnyflix.catalogo.CatalogoEspecies;
import br.com.bunnyflix.model.Produto;
import br.com.bunnyflix.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    /** Página de um produto: /produto/12-feno-premium-de-timoteo (só o número é obrigatório). */
    @GetMapping("/produto/{idSlug}")
    public String detalhe(@PathVariable String idSlug, Model model) {
        Produto produto = produtoService.buscarPorId(extrairId(idSlug))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String enderecoCorreto = produto.getId() + "-" + produto.getSlug();
        if (!enderecoCorreto.equals(idSlug)) {
            return "redirect:/produto/" + enderecoCorreto;
        }

        String descricao = produto.getDescricao() == null ? "" : produto.getDescricao();
        if (descricao.length() > 155) {
            descricao = descricao.substring(0, 152).trim() + "...";
        }

        model.addAttribute("paginaAtual", "catalogo");
        model.addAttribute("produto", produto);
        model.addAttribute("especieInfo", CatalogoEspecies.buscar(produto.getEspecie()));
        model.addAttribute("categoriaInfo", CatalogoCategorias.porValor().get(produto.getCategoria()));
        model.addAttribute("relacionados", produtoService.relacionados(produto, 4));
        model.addAttribute("tituloPagina", produto.getTitulo() + " – " + produto.getEspecie() + " | BunnyFlix");
        model.addAttribute("descricaoPagina", descricao);
        return "produto";
    }

    private static Long extrairId(String idSlug) {
        int fim = 0;
        while (fim < idSlug.length() && fim < 18 && Character.isDigit(idSlug.charAt(fim))) {
            fim++;
        }
        if (fim == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return Long.parseLong(idSlug.substring(0, fim));
    }
}
