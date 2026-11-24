package br.com.bunnyflix.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs_acesso")
public class LogAcesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String ipUsuario;
    private LocalDateTime dataAcesso;
    private boolean sucesso;

    public LogAcesso() {}

    public LogAcesso(Usuario usuario, String ipUsuario, LocalDateTime dataAcesso, boolean sucesso) {
        this.usuario = usuario;
        this.ipUsuario = ipUsuario;
        this.dataAcesso = dataAcesso;
        this.sucesso = sucesso;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getIpUsuario() { return ipUsuario; }
    public void setIpUsuario(String ipUsuario) { this.ipUsuario = ipUsuario; }

    public LocalDateTime getDataAcesso() { return dataAcesso; }
    public void setDataAcesso(LocalDateTime dataAcesso) { this.dataAcesso = dataAcesso; }

    public boolean isSucesso() { return sucesso; }
    public void setSucesso(boolean sucesso) { this.sucesso = sucesso; }
}
