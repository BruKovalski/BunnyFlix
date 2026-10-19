package br.com.bunnyflix.model;

import jakarta.persistence.*;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String categoria;
    private String especie;
    private Double preco;
    private Double avaliacao;
    private String duracao;
    private String imagemUrl;

    @Column(length = 1000)
    private String descricao;

    public Produto() {}

    public Produto(String titulo, String categoria, String especie, Double preco, Double avaliacao, String duracao, String imagemUrl, String descricao) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.especie = especie;
        this.preco = preco;
        this.avaliacao = avaliacao;
        this.duracao = duracao;
        this.imagemUrl = imagemUrl;
        this.descricao = descricao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public Double getAvaliacao() { return avaliacao; }
    public void setAvaliacao(Double avaliacao) { this.avaliacao = avaliacao; }

    public String getDuracao() { return duracao; }
    public void setDuracao(String duracao) { this.duracao = duracao; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
