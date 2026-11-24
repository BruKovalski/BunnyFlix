package br.com.bunnyflix.catalogo;

/** Tipo de produto (subcategoria). O {@code valor} é o texto gravado no banco. */
public class CategoriaProduto {

    private final String valor;
    private final String slug;
    private final String rotulo;
    private final String icone;
    private final String descricao;

    public CategoriaProduto(String valor, String slug, String rotulo, String icone, String descricao) {
        this.valor = valor;
        this.slug = slug;
        this.rotulo = rotulo;
        this.icone = icone;
        this.descricao = descricao;
    }

    public String getValor() { return valor; }
    public String getSlug() { return slug; }
    public String getRotulo() { return rotulo; }
    public String getIcone() { return icone; }
    public String getDescricao() { return descricao; }
}
