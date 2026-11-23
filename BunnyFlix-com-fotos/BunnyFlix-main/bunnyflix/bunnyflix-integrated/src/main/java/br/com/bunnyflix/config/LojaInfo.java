package br.com.bunnyflix.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/** Dados da loja lidos de application.properties (prefixo "loja."). Campos vazios não são exibidos. */
@Component
public class LojaInfo {

    private final String nome;
    private final String email;
    private final String telefone;
    private final String endereco;
    private final String horario;
    private final String instagram;

    public LojaInfo(@Value("${loja.nome:BunnyFlix}") String nome,
                    @Value("${loja.email:}") String email,
                    @Value("${loja.telefone:}") String telefone,
                    @Value("${loja.endereco:}") String endereco,
                    @Value("${loja.horario:}") String horario,
                    @Value("${loja.instagram:}") String instagram) {
        this.nome = nome;
        this.email = email.trim();
        this.telefone = telefone.trim();
        this.endereco = endereco.trim();
        this.horario = horario.trim();
        this.instagram = instagram.trim();
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getEndereco() { return endereco; }
    public String getHorario() { return horario; }
    public String getInstagram() { return instagram; }

    public boolean isTemContato() {
        return Stream.of(email, telefone, endereco, horario, instagram).anyMatch(v -> !v.isEmpty());
    }
}
