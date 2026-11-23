package br.com.bunnyflix.catalogo;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Tipos de produto exibidos na loja, na ordem em que aparecem nos menus. */
public final class CatalogoCategorias {

    private static final List<CategoriaProduto> TODAS = List.of(
        new CategoriaProduto("Alimentos", "alimentos", "Alimentação", "fa-utensils",
            "Rações, feno, sementes e petiscos."),
        new CategoriaProduto("Gaiolas", "gaiolas", "Habitats e abrigos", "fa-house",
            "Gaiolas, aquários, camas, tocas e casinhas."),
        new CategoriaProduto("Brinquedos", "brinquedos", "Brinquedos", "fa-puzzle-piece",
            "Itens para brincar, roer e se exercitar."),
        new CategoriaProduto("Acessórios", "acessorios", "Acessórios", "fa-toolbox",
            "Comedouros, bebedouros, coleiras, filtros e equipamentos."),
        new CategoriaProduto("Higiene", "higiene", "Higiene e cuidados", "fa-pump-soap",
            "Produtos de limpeza, banho e manutenção.")
    );

    private static final Map<String, CategoriaProduto> POR_VALOR = TODAS.stream()
        .collect(Collectors.toMap(CategoriaProduto::getValor, c -> c, (a, b) -> a, LinkedHashMap::new));

    private static final Map<String, CategoriaProduto> POR_SLUG = TODAS.stream()
        .collect(Collectors.toMap(CategoriaProduto::getSlug, c -> c, (a, b) -> a, LinkedHashMap::new));

    private CatalogoCategorias() {}

    public static List<CategoriaProduto> todas() {
        return TODAS;
    }

    public static Map<String, CategoriaProduto> porValor() {
        return Collections.unmodifiableMap(POR_VALOR);
    }

    public static CategoriaProduto buscarPorSlug(String slug) {
        return slug == null ? null : POR_SLUG.get(slug);
    }
}
