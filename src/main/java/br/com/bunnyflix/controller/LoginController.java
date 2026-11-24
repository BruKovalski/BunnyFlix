package br.com.bunnyflix.controller;

import br.com.bunnyflix.model.Usuario;
import br.com.bunnyflix.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    public static final String SESSAO_USUARIO = "usuarioLogado";

    @Autowired private AuthService authService;

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        return session.getAttribute(SESSAO_USUARIO) != null ? "redirect:/catalogo" : "login";
    }

    @PostMapping("/login")
    public String realizarLogin(@RequestParam String usuario, @RequestParam String senha,
                                HttpServletRequest request, Model model) {
        Optional<Usuario> autenticado = authService.autenticarEGravarAcesso(usuario, senha, request);
        if (autenticado.isPresent()) {
            HttpSession session = request.getSession(true);
            request.changeSessionId();
            session.setAttribute(SESSAO_USUARIO, autenticado.get().getUsuario());
            session.setAttribute("nomeUsuarioLogado", autenticado.get().getNome());
            return "redirect:/catalogo";
        }
        model.addAttribute("mensagem", "Usuário ou senha incorretos. Tente novamente.");
        return "login";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@RequestParam String usuario, @RequestParam String nome,
                            @RequestParam String senha, @RequestParam String confirmarSenha,
                            Model model) {
        String login = usuario == null ? "" : usuario.trim();
        String nomeLimpo = nome == null ? "" : nome.trim();
        if (nomeLimpo.length() > 80) {
            model.addAttribute("mensagemCadastro", "O nome deve ter no máximo 80 caracteres.");
            return "login";
        }
        if (!login.matches("[a-zA-Z0-9._-]{3,30}")) {
            model.addAttribute("mensagemCadastro", "Use de 3 a 30 caracteres: letras, números, ponto, hífen ou sublinhado.");
            return "login";
        }
        if (senha == null || senha.length() < 6) {
            model.addAttribute("mensagemCadastro", "A senha deve ter pelo menos 6 caracteres.");
            return "login";
        }
        if (!senha.equals(confirmarSenha)) {
            model.addAttribute("mensagemCadastro", "As senhas não coincidem.");
            return "login";
        }
        if (!authService.criarUsuario(login, senha, nomeLimpo)) {
            model.addAttribute("mensagemCadastro", "Este usuário já está cadastrado.");
            return "login";
        }
        model.addAttribute("mensagem", "Conta criada com sucesso! Entre com seu usuário e senha.");
        return "login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return "redirect:/login?logout";
    }
}
