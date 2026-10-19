package br.com.bunnyflix.controller;

import br.com.bunnyflix.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String realizarLogin(@RequestParam String usuario,
                                @RequestParam String senha,
                                HttpServletRequest request,
                                Model model) {

        boolean autenticado = authService.autenticarEGravarAcesso(usuario, senha, request);

        if (autenticado) {
            model.addAttribute("mensagem", "Login realizado com sucesso! Registro gravado no banco de dados.");
            model.addAttribute("usuarioNome", usuario);
            model.addAttribute("logs", authService.obterUltimosAcessos());
            return "painel";
        } else {
            model.addAttribute("mensagem", "Usuário ou senha incorretos! Tente novamente.");
            return "login";
        }
    }
}
