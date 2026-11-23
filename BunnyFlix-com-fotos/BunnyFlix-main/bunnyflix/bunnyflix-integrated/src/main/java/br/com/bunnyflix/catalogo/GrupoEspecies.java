package br.com.bunnyflix.catalogo;

import br.com.bunnyflix.util.Slug;

/** Setor da loja que reúne várias espécies (ex.: Aves, Peixes). */
public class GrupoEspecies {

    private final String nome;
    private final String slug;
    private final String icone;
    private final String descricao;

    public GrupoEspecies(String nome, String icone, String descricao) {
        this.nome = nome;
        this.slug = Slug.de(nome);
        this.icone = icone;
        this.descricao = descricao;
    }

    public String getNome() { return nome; }
    public String getSlug() { return slug; }
    public String getIcone() { return icone; }
    public String getDescricao() { return descricao; }
}
