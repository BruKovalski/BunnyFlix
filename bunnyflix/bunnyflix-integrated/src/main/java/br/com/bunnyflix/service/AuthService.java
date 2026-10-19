package br.com.bunnyflix.service;

import br.com.bunnyflix.model.LogAcesso;
import br.com.bunnyflix.model.Usuario;
import br.com.bunnyflix.repository.LogAcessoRepository;
import br.com.bunnyflix.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LogAcessoRepository logAcessoRepository;

    public boolean autenticarEGravarAcesso(String usuario, String senha, HttpServletRequest request) {
        Optional<Usuario> userOpt = usuarioRepository.findByUsuario(usuario);
        String clientIp = request.getRemoteAddr();

        if (userOpt.isPresent() && userOpt.get().getSenha().equals(senha)) {
            Usuario user = userOpt.get();
            // Grava o acesso bem-sucedido no banco de dados H2
            LogAcesso log = new LogAcesso(user, clientIp, LocalDateTime.now(), true);
            logAcessoRepository.save(log);
            return true;
        } else {
            userOpt.ifPresent(user -> {
                LogAcesso log = new LogAcesso(user, clientIp, LocalDateTime.now(), false);
                logAcessoRepository.save(log);
            });
            return false;
        }
    }

    public List<LogAcesso> obterUltimosAcessos() {
        return logAcessoRepository.findTop10ByOrderByIdDesc();
    }
}
