package br.com.bunnyflix.catalogo;

import br.com.bunnyflix.util.Slug;

/**
 * Espécie de pet atendida pela loja. Não é uma entidade do banco: os dados
 * ficam em {@link CatalogoEspecies} e servem para filtros, páginas e cores.
 */
public class Especie {

    private final String nome;
    private final String slug;
    private final GrupoEspecies grupo;
    private final String cor;
    private final String cientifico;
    private final String curiosidade;

    public Especie(String nome, GrupoEspecies grupo, String cor, String cientifico, String curiosidade) {
        this.nome = nome;
        this.slug = Slug.de(nome);
        this.grupo = grupo;
        this.cor = cor;
        this.cientifico = cientifico;
        this.curiosidade = curiosidade;
    }

    public String getNome() { return nome; }
    public String getSlug() { return slug; }
    public GrupoEspecies getGrupo() { return grupo; }
    public String getIcone() { return grupo.getIcone(); }
    public String getCor() { return cor; }
    public String getCientifico() { return cientifico; }
    public String getCuriosidade() { return curiosidade; }
}
