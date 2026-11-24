package br.com.bunnyflix.catalogo;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Catálogo fixo das espécies exibidas na loja. O campo {@code especie} de cada
 * produto deve usar exatamente o nome cadastrado aqui.
 */
public final class CatalogoEspecies {

    public static final GrupoEspecies CAES_GATOS = new GrupoEspecies("Cães e Gatos", "fa-paw",
        "Alimentação, camas, brinquedos, acessórios e higiene para cães e gatos.");
    public static final GrupoEspecies MAMIFEROS = new GrupoEspecies("Pequenos Mamíferos", "fa-paw",
        "Coelhos, porquinhos-da-índia, hamsters e outros roedores de estimação.");
    public static final GrupoEspecies AVES = new GrupoEspecies("Aves", "fa-dove",
        "Sementes, rações, gaiolas e acessórios para aves domésticas.");
    public static final GrupoEspecies PEIXES = new GrupoEspecies("Peixes", "fa-fish",
        "Aquários, filtros, alimentação e cuidados para peixes ornamentais.");

    private static final List<GrupoEspecies> GRUPOS = List.of(CAES_GATOS, MAMIFEROS, AVES, PEIXES);

    private static final List<Especie> TODAS = List.of(
        // Pequenos mamíferos
        new Especie("Coelhos", MAMIFEROS, "#c4895b", "Oryctolagus cuniculus",
            "Herbívoros de digestão delicada: o feno deve ser a base da dieta e ficar sempre à disposição."),
        new Especie("Porquinhos-da-Índia", MAMIFEROS, "#d9a441", "Cavia porcellus",
            "Não produzem vitamina C e precisam recebê-la todos os dias pela alimentação. São animais sociáveis."),
        new Especie("Hamsters", MAMIFEROS, "#e0813b", "Mesocricetus auratus (hamster-sírio) e outras espécies",
            "Têm hábitos noturnos e percorrem longas distâncias durante a noite, por isso a roda precisa ser grande e silenciosa."),
        new Especie("Chinchilas", MAMIFEROS, "#7a8ba3", "Chinchilla lanigera",
            "Originárias dos Andes, têm uma pelagem muito densa e tomam banho de pó, nunca de água. Sofrem com o calor."),
        new Especie("Gerbils", MAMIFEROS, "#9c7b4c", "Meriones unguiculatus",
            "Vêm de regiões áridas da Mongólia e da China, adoram escavar e precisam de uma camada profunda de substrato."),
        new Especie("Ratos Twister", MAMIFEROS, "#8d7b9b", "Rattus norvegicus (forma domesticada)",
            "Inteligentes e sociáveis, gostam de escalar, explorar e viver em grupo."),
        new Especie("Camundongos", MAMIFEROS, "#8e9aa5", "Mus musculus",
            "Pequenos e muito ativos, gostam de esconderijos, túneis e de roer."),

        // Cães e gatos
        new Especie("Cães", CAES_GATOS, "#3f8fbf", "Canis lupus familiaris",
            "Companheiros do ser humano há milhares de anos, precisam de exercício, convívio e alimentação adequada ao porte e à idade."),
        new Especie("Gatos", CAES_GATOS, "#cf6a8a", "Felis catus",
            "Carnívoros estritos, precisam de ração formulada para a espécie, além de arranhadores e lugares altos para explorar."),

        // Aves
        new Especie("Calopsitas", AVES, "#e2c13f", "Nymphicus hollandicus",
            "Originárias da Austrália, usam a crista para expressar emoções e precisam de convívio e de brinquedos para roer."),
        new Especie("Periquitos-australianos", AVES, "#43b581", "Melopsittacus undulatus",
            "Pequenos e sociáveis, também vêm da Austrália e vivem melhor com estímulos e companhia."),
        new Especie("Agapornis", AVES, "#e0585a", "Agapornis spp. (ex.: Agapornis roseicollis)",
            "Conhecidos como inseparáveis, são aves de origem africana, muito sociáveis e ativas."),
        new Especie("Canários", AVES, "#f0b429", "Serinus canaria",
            "Originários das ilhas Canárias, da Madeira e dos Açores, são famosos pelo canto dos machos."),

        // Peixes
        new Especie("Bettas", PEIXES, "#7b4fd1", "Betta splendens",
            "Originários do Sudeste Asiático, respiram ar na superfície e os machos costumam brigar entre si."),
        new Especie("Kinguios", PEIXES, "#ef7d2a", "Carassius auratus",
            "Descendem de carpas criadas na China, crescem bastante e produzem muita sujeira, por isso pedem aquário grande e boa filtragem."),
        new Especie("Guppies", PEIXES, "#2ab7a9", "Poecilia reticulata",
            "Peixes vivíparos de origem sul-americana: os filhotes nascem já nadando."),
        new Especie("Neons", PEIXES, "#2f7de1", "Paracheirodon innesi",
            "Peixes de cardume originários da bacia amazônica, vivem melhor em grupo.")
    );

    private static final Map<String, Especie> POR_NOME = TODAS.stream()
        .collect(Collectors.toMap(Especie::getNome, e -> e, (a, b) -> a, LinkedHashMap::new));

    private static final Map<String, Especie> POR_SLUG = TODAS.stream()
        .collect(Collectors.toMap(Especie::getSlug, e -> e, (a, b) -> a, LinkedHashMap::new));

    private CatalogoEspecies() {}

    public static List<Especie> todas() {
        return TODAS;
    }

    public static Map<String, Especie> porNome() {
        return Collections.unmodifiableMap(POR_NOME);
    }

    public static List<GrupoEspecies> grupos() {
        return GRUPOS;
    }

    public static GrupoEspecies grupoPorSlug(String slug) {
        return GRUPOS.stream().filter(g -> g.getSlug().equals(slug)).findFirst().orElse(null);
    }

    public static List<Especie> doGrupo(GrupoEspecies grupo) {
        return TODAS.stream().filter(e -> e.getGrupo() == grupo).toList();
    }

    public static Especie buscarPorSlug(String slug) {
        return slug == null ? null : POR_SLUG.get(slug);
    }

    public static Especie buscar(String nome) {
        return nome == null ? null : POR_NOME.get(nome);
    }
}
