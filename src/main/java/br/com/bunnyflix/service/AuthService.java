package br.com.bunnyflix.service;

import br.com.bunnyflix.model.LogAcesso;
import br.com.bunnyflix.model.Usuario;
import br.com.bunnyflix.repository.LogAcessoRepository;
import br.com.bunnyflix.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private LogAcessoRepository logAcessoRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Transactional
    public Optional<Usuario> autenticarEGravarAcesso(String usuario, String senha, HttpServletRequest request) {
        String login = normalizarUsuario(usuario);
        Optional<Usuario> userOpt = usuarioRepository.findByUsuario(login);
        String clientIp = request.getRemoteAddr();

        if (userOpt.isPresent() && passwordEncoder.matches(senha, userOpt.get().getSenha())) {
            Usuario user = userOpt.get();
            logAcessoRepository.save(new LogAcesso(user, clientIp, LocalDateTime.now(), true));
            return Optional.of(user);
        }

        userOpt.ifPresent(user -> logAcessoRepository.save(
            new LogAcesso(user, clientIp, LocalDateTime.now(), false)));
        return Optional.empty();
    }

    @Transactional
    public boolean criarUsuario(String usuario, String senha, String nome) {
        String login = normalizarUsuario(usuario);
        if (login.isBlank() || senha == null || senha.length() < 6
                || usuarioRepository.findByUsuario(login).isPresent()) {
            return false;
        }
        usuarioRepository.save(new Usuario(login, passwordEncoder.encode(senha),
            nome == null || nome.isBlank() ? login : nome.trim()));
        return true;
    }

    public boolean usuarioExiste(String usuario) {
        return usuarioRepository.findByUsuario(normalizarUsuario(usuario)).isPresent();
    }

    public List<LogAcesso> obterUltimosAcessos() {
        return logAcessoRepository.findTop10ByOrderByIdDesc();
    }

    private String normalizarUsuario(String usuario) {
        return usuario == null ? "" : usuario.trim().toLowerCase(Locale.ROOT);
    }
}
