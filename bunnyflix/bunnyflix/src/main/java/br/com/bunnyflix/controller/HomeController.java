package br.com.bunnyflix.controller;

import br.com.bunnyflix.model.Produto;
import br.com.bunnyflix.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) String especie) {
        List<Produto> todos;
        if (especie != null && !especie.isEmpty()) {
            todos = produtoService.buscarPorEspecie(especie);
        } else {
            todos = produtoService.listarTodos();
        }

        model.addAttribute("destaques", todos.stream().filter(p -> "Alimentos".equals(p.getCategoria())).toList());
        model.addAttribute("casinhas", todos.stream().filter(p -> "Gaiolas".equals(p.getCategoria())).toList());
        model.addAttribute("brinquedos", todos.stream().filter(p -> "Brinquedos".equals(p.getCategoria())).toList());
        model.addAttribute("acessorios", todos.stream().filter(p -> "Acessórios".equals(p.getCategoria())).toList());
        model.addAttribute("todosProdutos", todos);
        model.addAttribute("especieSelecionada", especie);

        return "index";
    }
}
