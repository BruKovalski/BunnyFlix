package br.com.bunnyflix;

import br.com.bunnyflix.model.Produto;
import br.com.bunnyflix.model.Usuario;
import br.com.bunnyflix.repository.ProdutoRepository;
import br.com.bunnyflix.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataLoader {

    private static final String ALIMENTOS = "Alimentos";
    private static final String GAIOLAS = "Gaiolas";
    private static final String BRINQUEDOS = "Brinquedos";
    private static final String ACESSORIOS = "Acessórios";
    private static final String HIGIENE = "Higiene";

    @Bean
    CommandLineRunner initDatabase(ProdutoRepository produtoRepo, UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepo.count() == 0) {
                usuarioRepo.save(new Usuario("tricolor", passwordEncoder.encode("1903"), "Gremista Tricolor"));
                usuarioRepo.save(new Usuario("gremio", passwordEncoder.encode("1903"), "Grêmio FBPA"));
            }

            if (produtoRepo.count() == 0) {
                produtoRepo.saveAll(catalogoInicial());
            }
        };
    }

    /**
     * Produtos de demonstração. Preços e avaliações são valores ilustrativos.
     * O nome da espécie deve ser igual ao cadastrado em CatalogoEspecies.
     * As fotos são opcionais: sem imagemUrl o cartão mostra um ícone do tipo de produto.
     */
    private static List<Produto> catalogoInicial() {
        List<Produto> l = new ArrayList<>();

        // ---------- Coelhos ----------
        l.add(new Produto("Feno Premium de Timóteo", ALIMENTOS, "Coelhos", 49.90, 4.9, "Pacote de 1 kg", null,
            "Feno de timóteo selecionado à mão, ideal para a saúde digestiva e para o desgaste natural dos dentes de coelhos e porquinhos-da-índia."));
        l.add(new Produto("Ração Extrusada para Coelhos Adultos", ALIMENTOS, "Coelhos", 38.90, 4.7, "Pacote de 1,5 kg", null,
            "Ração em pellets rica em fibras, para complementar o feno na rotina de coelhos adultos."));
        l.add(new Produto("Mansão Deluxe em Madeira Maciça", GAIOLAS, "Coelhos", 389.00, 4.9, "2 andares – 120 cm", null,
            "Habitat espaçoso com rampas antiderrapantes, bandeja removível para higienização rápida e acabamento atóxico."));
        l.add(new Produto("Cercadinho Dobrável de Metal", GAIOLAS, "Coelhos", 179.90, 4.6, "8 painéis de 60 cm", null,
            "Espaço seguro para o coelho se exercitar dentro ou fora de casa, fácil de montar e de guardar."));
        l.add(new Produto("Esconderijo de Madeira com Telhado", GAIOLAS, "Coelhos", 74.90, 4.8, "Tamanho M", null,
            "Toca de madeira sem tratamento químico, ideal para dar segurança e privacidade ao coelho."));
        l.add(new Produto("Túnel Flexível de Madeira e Salgueiro", BRINQUEDOS, "Coelhos", 64.90, 4.8, "Extensível, 50 cm", null,
            "Túnel moldável de galhos comestíveis, ideal para roer, se esconder e gastar energia no dia a dia."));
        l.add(new Produto("Cenourinhas Mordedoras de Salgueiro", BRINQUEDOS, "Coelhos", 29.90, 4.9, "Pacote com 3 unidades", null,
            "Mordedores divertidos em formato de cenoura, feitos de fibras atóxicas, para ajudar no desgaste dos dentes."));
        l.add(new Produto("Bebedouro Automático Antivazamento", ACESSORIOS, "Coelhos", 42.00, 4.7, "Capacidade de 500 ml", null,
            "Bico duplo em aço inox de alta durabilidade, com presilha universal ajustável para gaiolas."));
        l.add(new Produto("Escova de Pelos com Cerdas Macias", HIGIENE, "Coelhos", 32.50, 4.8, "Cabo ergonômico", null,
            "Remove os pelos mortos com delicadeza, sem machucar a pele sensível dos pequenos animais."));
        l.add(new Produto("Caixa Sanitária de Canto com Grade", HIGIENE, "Coelhos", 54.90, 4.5, "Tamanho G", null,
            "Bandeja de canto com grade removível, que ajuda a manter o coelho longe da sujeira."));
        l.add(new Produto("Granulado de Papel Reciclado", HIGIENE, "Coelhos", 27.90, 4.7, "Pacote de 10 L", null,
            "Granulado macio, de baixa poeira e boa absorção, para forrar a caixa sanitária."));

        // ---------- Porquinhos-da-Índia ----------
        l.add(new Produto("Ração Super Premium Gourmet para Porquinhos-da-Índia", ALIMENTOS, "Porquinhos-da-Índia", 35.50, 4.8, "Embalagem de 800 g", null,
            "Nutrição enriquecida com vitamina C estabilizada, sem corantes artificiais e com alta digestibilidade."));
        l.add(new Produto("Feno de Timóteo Macio", ALIMENTOS, "Porquinhos-da-Índia", 44.90, 4.8, "Pacote de 1 kg", null,
            "Feno verde e macio, que deve ser a base da alimentação dos porquinhos-da-índia."));
        l.add(new Produto("Suplemento de Vitamina C em Pó", ALIMENTOS, "Porquinhos-da-Índia", 28.90, 4.6, "Pote de 50 g", null,
            "Complemento para a dieta, já que os porquinhos-da-índia não produzem a própria vitamina C. Siga a orientação do veterinário."));
        l.add(new Produto("Gaiola Ampla com Rampa e Base Funda", GAIOLAS, "Porquinhos-da-Índia", 259.00, 4.7, "100 cm de comprimento", null,
            "Gaiola espaçosa, com base funda e rampa de acesso, pensada para a rotina dos porquinhos."));
        l.add(new Produto("Toca Estilo Cabana Escandinava", GAIOLAS, "Porquinhos-da-Índia", 89.90, 4.8, "Tamanho G", null,
            "Refúgio aconchegante e acolchoado, com tecido lavável, para momentos de descanso com segurança."));
        l.add(new Produto("Bola de Erva-dos-Prados com Guizo", BRINQUEDOS, "Porquinhos-da-Índia", 24.90, 4.5, "Diâmetro de 12 cm", null,
            "Brinquedo interativo 100% natural, que estimula o exercício físico e ajuda a combater o estresse."));
        l.add(new Produto("Casinha de Feno Comestível", BRINQUEDOS, "Porquinhos-da-Índia", 26.90, 4.7, "Tamanho P", null,
            "Casinha de feno prensado que serve de esconderijo e de petisco."));
        l.add(new Produto("Porta-Feno Suspenso de Metal", ACESSORIOS, "Porquinhos-da-Índia", 38.90, 4.6, "Suporte duplo", null,
            "Mantém o feno limpo e livre de sujeira dentro da gaiola, com um suporte inteligente."));
        l.add(new Produto("Comedouro de Cerâmica Antitombo", ACESSORIOS, "Porquinhos-da-Índia", 31.90, 4.8, "Diâmetro de 12 cm", null,
            "Base pesada, que evita que o comedouro vire, e fácil de lavar."));
        l.add(new Produto("Forro de Fibra de Papel", HIGIENE, "Porquinhos-da-Índia", 49.90, 4.6, "Pacote de 30 L", null,
            "Forro macio e de alta absorção para a base da gaiola."));

        // ---------- Hamsters ----------
        l.add(new Produto("Petisco Crocante de Maçã e Cenoura", ALIMENTOS, "Hamsters", 18.90, 4.7, "Pacote de 150 g", null,
            "Biscoitinhos assados e naturais, ótimos para variar a alimentação e premiar o hamster nos treinos."));
        l.add(new Produto("Mix de Sementes e Grãos para Hamsters", ALIMENTOS, "Hamsters", 21.90, 4.6, "Pacote de 500 g", null,
            "Mistura de grãos e sementes para a rotina alimentar de hamsters adultos."));
        l.add(new Produto("Gaiola Panorâmica Glass View", GAIOLAS, "Hamsters", 210.00, 4.6, "Modelo compacto Deluxe", null,
            "Gaiola panorâmica transparente, com ventilação otimizada, que ajuda a evitar fugas e oferece visão de 360°."));
        l.add(new Produto("Esconderijo de Cerâmica Meia-Lua", GAIOLAS, "Hamsters", 34.90, 4.8, "Tamanho P", null,
            "Toca de cerâmica que mantém o ambiente fresco e dá privacidade ao hamster."));
        l.add(new Produto("Roda Silenciosa Turbo Spinner", BRINQUEDOS, "Hamsters", 79.90, 5.0, "Diâmetro de 28 cm", null,
            "Rolamento duplo ultrassilencioso. O tamanho da roda ajuda a evitar a curvatura da coluna do seu pequeno roedor."));
        l.add(new Produto("Túneis de Tubos Conectáveis", BRINQUEDOS, "Hamsters", 59.90, 4.5, "Kit com 6 peças", null,
            "Tubos que se encaixam para montar caminhos e incentivar a exploração."));
        l.add(new Produto("Mordedores de Madeira Natural", BRINQUEDOS, "Hamsters", 17.90, 4.7, "Kit com 6 unidades", null,
            "Peças de madeira sem tratamento, para roer e ajudar no desgaste dos dentes."));
        l.add(new Produto("Bebedouro de Bico com Suporte", ACESSORIOS, "Hamsters", 16.90, 4.4, "Capacidade de 80 ml", null,
            "Bebedouro pequeno, com bico de aço, para fixar na grade da gaiola."));
        l.add(new Produto("Areia de Banho para Hamsters", HIGIENE, "Hamsters", 19.90, 4.6, "Pote de 500 g", null,
            "Areia fina e sem perfume, própria para o banho a seco do hamster."));

        // ---------- Chinchilas ----------
        l.add(new Produto("Ração Extrusada Especial Chinchila Supreme", ALIMENTOS, "Chinchilas", 52.00, 5.0, "Saco de 1,2 kg", null,
            "Fórmula equilibrada, rica em fibras de alfafa e minerais essenciais para uma pelagem saudável e brilhante."));
        l.add(new Produto("Feno de Capim Selecionado", ALIMENTOS, "Chinchilas", 39.90, 4.8, "Pacote de 500 g", null,
            "Fibras longas e macias, importantes para a digestão e para o desgaste dos dentes."));
        l.add(new Produto("Gaiola Vertical de Dois Andares", GAIOLAS, "Chinchilas", 459.00, 4.8, "150 cm de altura", null,
            "Gaiola alta, com prateleiras de madeira, para as chinchilas saltarem e escalarem."));
        l.add(new Produto("Playground Suspenso com Pontes", GAIOLAS, "Chinchilas", 145.00, 4.7, "Kit com 5 peças", null,
            "Estruturas modulares de madeira natural, para fixar nas grades e incentivar saltos e escaladas."));
        l.add(new Produto("Roda de Metal Silenciosa", BRINQUEDOS, "Chinchilas", 149.90, 4.7, "Diâmetro de 33 cm", null,
            "Roda ampla, de superfície lisa e sem frestas, para a chinchila se exercitar com segurança."));
        l.add(new Produto("Prateleira de Madeira de Macieira", BRINQUEDOS, "Chinchilas", 28.90, 4.7, "Tamanho M", null,
            "Madeira natural, própria para roer e para subir."));
        l.add(new Produto("Bebedouro de Bico em Aço Inox", ACESSORIOS, "Chinchilas", 23.90, 4.6, "Capacidade de 250 ml", null,
            "Bico de aço inox, resistente a mordidas, com presilha para a grade."));
        l.add(new Produto("Banheiro de Areia Vulcânica", HIGIENE, "Chinchilas", 59.90, 4.9, "Kit banho de pó", null,
            "Recipiente fechado que evita sujeira para fora, ideal para o banho a seco, essencial para as chinchilas."));
        l.add(new Produto("Pó de Banho para Chinchilas", HIGIENE, "Chinchilas", 34.90, 4.9, "Pote de 1 kg", null,
            "Pó fino, próprio para o banho a seco, que ajuda a manter a pelagem limpa e densa."));

        // ---------- Gerbils ----------
        l.add(new Produto("Mistura de Grãos para Gerbils", ALIMENTOS, "Gerbils", 22.90, 4.6, "Pacote de 500 g", null,
            "Mistura de grãos e sementes para a rotina alimentar dos gerbils."));
        l.add(new Produto("Aquário de Escavação com Tampa Telada", GAIOLAS, "Gerbils", 289.00, 4.8, "Comprimento de 60 cm", null,
            "Aquário amplo, que comporta uma camada funda de substrato e incentiva a escavação."));
        l.add(new Produto("Casinha de Madeira com Entrada Dupla", GAIOLAS, "Gerbils", 39.90, 4.6, "Tamanho P", null,
            "Esconderijo de madeira com duas entradas, para explorar e descansar."));
        l.add(new Produto("Túnel de Papelão Natural", BRINQUEDOS, "Gerbils", 14.90, 4.5, "Kit com 3 peças", null,
            "Peças de papelão natural, sem tintas, para roer e brincar."));
        l.add(new Produto("Roda de Superfície Lisa", BRINQUEDOS, "Gerbils", 59.90, 4.6, "Diâmetro de 20 cm", null,
            "Roda sem frestas, que protege as patas e a cauda durante o exercício."));
        l.add(new Produto("Substrato de Fibra de Papel para Escavar", HIGIENE, "Gerbils", 39.90, 4.7, "Pacote de 20 L", null,
            "Substrato macio e absorvente, ideal para cavar túneis."));

        // ---------- Ratos Twister ----------
        l.add(new Produto("Ração em Blocos para Ratos de Estimação", ALIMENTOS, "Ratos Twister", 29.90, 4.7, "Pacote de 1 kg", null,
            "Ração balanceada, em blocos, formulada para ratos e camundongos."));
        l.add(new Produto("Gaiola de Vários Andares com Rampas", GAIOLAS, "Ratos Twister", 349.00, 4.8, "3 andares – 90 cm", null,
            "Gaiola alta, com rampas e plataformas, para os ratos escalarem e explorarem."));
        l.add(new Produto("Túnel de Corda Trançada", BRINQUEDOS, "Ratos Twister", 31.90, 4.6, "Comprimento de 40 cm", null,
            "Corda trançada de fibra natural, para escalar e se esconder."));
        l.add(new Produto("Roda de Exercício para Ratos", BRINQUEDOS, "Ratos Twister", 119.90, 4.7, "Diâmetro de 32 cm", null,
            "Roda ampla, de superfície lisa, para gastar energia com segurança."));
        l.add(new Produto("Rede de Tecido Suspensa", ACESSORIOS, "Ratos Twister", 36.90, 4.8, "Tamanho M", null,
            "Cantinho de descanso suspenso, de tecido lavável."));
        l.add(new Produto("Substrato Biodegradável de Celulose", HIGIENE, "Ratos Twister", 44.90, 4.6, "Pacote de 25 L", null,
            "Forro macio, de boa absorção e biodegradável, para a base da gaiola."));

        // ---------- Camundongos ----------
        l.add(new Produto("Mix de Sementes para Camundongos", ALIMENTOS, "Camundongos", 17.90, 4.5, "Pacote de 400 g", null,
            "Mistura de sementes e grãos para a rotina alimentar de camundongos."));
        l.add(new Produto("Casinha de Madeira com Escada", GAIOLAS, "Camundongos", 42.90, 4.7, "Tamanho P", null,
            "Esconderijo de madeira com escadinha, para subir, descansar e explorar."));
        l.add(new Produto("Roda Silenciosa Mini", BRINQUEDOS, "Camundongos", 49.90, 4.6, "Diâmetro de 18 cm", null,
            "Roda leve e silenciosa, no tamanho ideal para camundongos."));
        l.add(new Produto("Escadinha de Madeira para Roer", BRINQUEDOS, "Camundongos", 19.90, 4.5, "Comprimento de 20 cm", null,
            "Brinquedo de madeira natural, para escalar e roer."));
        l.add(new Produto("Bebedouro de Bico Mini", ACESSORIOS, "Camundongos", 14.90, 4.4, "Capacidade de 60 ml", null,
            "Bebedouro pequeno, com bico de aço, para fixar na grade."));

        // ---------- Cães ----------
        l.add(new Produto("Ração Premium para Cães Adultos", ALIMENTOS, "Cães", 189.90, 4.8, "Pacote de 10,1 kg", null,
            "Ração completa e balanceada, desenvolvida para a rotina de cães adultos."));
        l.add(new Produto("Biscoito Assado Sabor Frango", ALIMENTOS, "Cães", 19.90, 4.7, "Pacote de 250 g", null,
            "Petisco crocante, ótimo para premiar e para o adestramento."));
        l.add(new Produto("Cama Redonda Acolchoada Antiderrapante", GAIOLAS, "Cães", 139.90, 4.8, "Tamanho M", null,
            "Cama macia, com base antiderrapante e capa removível para lavar."));
        l.add(new Produto("Casinha de Plástico Resistente", GAIOLAS, "Cães", 329.00, 4.6, "Tamanho 3", null,
            "Casinha de plástico reforçado, fácil de limpar e de montar."));
        l.add(new Produto("Bolinha de Borracha Maciça", BRINQUEDOS, "Cães", 24.90, 4.7, "Diâmetro de 6 cm", null,
            "Bolinha resistente, que quica e é ótima para brincar de buscar."));
        l.add(new Produto("Corda com Nós para Cabo de Guerra", BRINQUEDOS, "Cães", 29.90, 4.6, "Comprimento de 40 cm", null,
            "Corda de algodão com nós, para brincar e roer."));
        l.add(new Produto("Coleira Ajustável com Fivela", ACESSORIOS, "Cães", 39.90, 4.6, "Tamanho M", null,
            "Coleira de nylon com fivela de encaixe rápido e ajuste de tamanho."));
        l.add(new Produto("Comedouro de Inox Antiderrapante", ACESSORIOS, "Cães", 44.90, 4.8, "Capacidade de 900 ml", null,
            "Tigela de aço inox, com base de borracha antiderrapante, fácil de higienizar."));
        l.add(new Produto("Shampoo Neutro para Cães", HIGIENE, "Cães", 32.90, 4.7, "Frasco de 500 ml", null,
            "Shampoo de fórmula neutra, para banhos frequentes."));
        l.add(new Produto("Escova Slicker Autolimpante", HIGIENE, "Cães", 49.90, 4.8, "Tamanho M", null,
            "Escova com botão que solta os pelos acumulados, para desembaraçar e remover pelos mortos."));

        // ---------- Gatos ----------
        l.add(new Produto("Ração Premium para Gatos Adultos", ALIMENTOS, "Gatos", 129.90, 4.8, "Pacote de 3 kg", null,
            "Ração completa e balanceada, formulada para gatos adultos."));
        l.add(new Produto("Sachê de Frango ao Molho", ALIMENTOS, "Gatos", 3.90, 4.7, "Sachê de 85 g", null,
            "Alimento úmido, ótimo para variar o cardápio e estimular a ingestão de água."));
        l.add(new Produto("Cama Iglu de Feltro", GAIOLAS, "Gatos", 109.90, 4.8, "Tamanho M", null,
            "Toca macia de feltro, que dá aconchego e privacidade ao gato."));
        l.add(new Produto("Arranhador Torre com Plataformas", BRINQUEDOS, "Gatos", 249.90, 4.8, "Altura de 120 cm", null,
            "Torre com postes de sisal e plataformas, para arranhar, escalar e descansar."));
        l.add(new Produto("Varinha com Penas", BRINQUEDOS, "Gatos", 17.90, 4.7, "Comprimento de 90 cm", null,
            "Brinquedo interativo, que estimula o instinto de caça e a brincadeira com o tutor."));
        l.add(new Produto("Fonte de Água com Filtro", ACESSORIOS, "Gatos", 129.90, 4.8, "Capacidade de 2 L", null,
            "Fonte com água em movimento e filtro, para incentivar o gato a beber mais."));
        l.add(new Produto("Caixa de Transporte Rígida", ACESSORIOS, "Gatos", 119.90, 4.7, "Tamanho M", null,
            "Caixa com ventilação lateral e porta de metal, para passeios e idas ao veterinário."));
        l.add(new Produto("Caixa de Areia Coberta", HIGIENE, "Gatos", 119.90, 4.6, "Tamanho G", null,
            "Caixa fechada, com tampa e porta vai e vem, que ajuda a conter odores e espalhamento de areia."));
        l.add(new Produto("Areia Higiênica de Sílica", HIGIENE, "Gatos", 36.90, 4.7, "Pacote de 1,6 kg", null,
            "Grãos de sílica, de alta absorção e baixa poeira."));

        // ---------- Calopsitas ----------
        l.add(new Produto("Mix de Sementes para Calopsitas", ALIMENTOS, "Calopsitas", 24.90, 4.7, "Pacote de 500 g", null,
            "Mistura de sementes selecionadas para a rotina alimentar das calopsitas."));
        l.add(new Produto("Ração Extrusada para Calopsitas", ALIMENTOS, "Calopsitas", 31.90, 4.8, "Pacote de 600 g", null,
            "Ração balanceada e completa, que evita a seleção de sementes e o desperdício."));
        l.add(new Produto("Gaiola Ampla com Poleiros", GAIOLAS, "Calopsitas", 389.00, 4.7, "Altura de 80 cm", null,
            "Gaiola espaçosa, com poleiros em alturas diferentes e porta ampla para facilitar o manejo."));
        l.add(new Produto("Balanço de Madeira Natural", BRINQUEDOS, "Calopsitas", 21.90, 4.6, "Tamanho M", null,
            "Balanço de madeira, que estimula a brincadeira e o equilíbrio."));
        l.add(new Produto("Poleiros de Galho Natural", ACESSORIOS, "Calopsitas", 18.90, 4.7, "Kit com 3 unidades", null,
            "Galhos naturais de espessuras variadas, que ajudam a exercitar os pés."));
        l.add(new Produto("Pedra de Cálcio com Sépia", ACESSORIOS, "Calopsitas", 12.90, 4.6, "Unidade", null,
            "Fonte de cálcio, que também ajuda a manter o bico em boa forma."));

        // ---------- Periquitos-australianos ----------
        l.add(new Produto("Mix de Painço e Alpiste", ALIMENTOS, "Periquitos-australianos", 19.90, 4.7, "Pacote de 500 g", null,
            "Mistura clássica de sementes para a alimentação de periquitos-australianos."));
        l.add(new Produto("Espigas de Painço", ALIMENTOS, "Periquitos-australianos", 9.90, 4.9, "Pacote com 5 espigas", null,
            "Petisco natural, que estimula a interação e o hábito de bicar."));
        l.add(new Produto("Gaiola Compacta com Acessórios", GAIOLAS, "Periquitos-australianos", 179.90, 4.6, "Comprimento de 50 cm", null,
            "Gaiola compacta, com poleiros, comedouros e bebedouro incluídos."));
        l.add(new Produto("Escada de Madeira com Sininho", BRINQUEDOS, "Periquitos-australianos", 14.90, 4.5, "Comprimento de 25 cm", null,
            "Escadinha de madeira, que incentiva o movimento e a diversão."));
        l.add(new Produto("Comedouro Antidesperdício", ACESSORIOS, "Periquitos-australianos", 15.90, 4.5, "Tamanho P", null,
            "Comedouro com proteção lateral, que reduz a sujeira de cascas de semente."));
        l.add(new Produto("Banheira de Pendurar", HIGIENE, "Periquitos-australianos", 24.90, 4.6, "Tamanho P", null,
            "Banheira de plástico com gancho, para o banho das aves dentro da gaiola."));

        // ---------- Agapornis ----------
        l.add(new Produto("Ração Extrusada para Agapornis", ALIMENTOS, "Agapornis", 34.90, 4.7, "Pacote de 500 g", null,
            "Ração balanceada, formulada para agapornis."));
        l.add(new Produto("Gaiola Média com Porta Frontal", GAIOLAS, "Agapornis", 219.00, 4.6, "Comprimento de 60 cm", null,
            "Gaiola com porta frontal ampla, que facilita a limpeza e a saída da ave."));
        l.add(new Produto("Ninho de Palha Natural", GAIOLAS, "Agapornis", 39.90, 4.7, "Diâmetro de 15 cm", null,
            "Ninho de palha trançada, que oferece aconchego e um lugar para descansar."));
        l.add(new Produto("Brinquedos de Madeira para Bicar", BRINQUEDOS, "Agapornis", 22.90, 4.7, "Kit com 4 peças", null,
            "Peças de madeira colorida, para bicar, roer e se distrair."));
        l.add(new Produto("Corda de Algodão para Escalar", BRINQUEDOS, "Agapornis", 19.90, 4.5, "Comprimento de 60 cm", null,
            "Corda trançada de algodão, para escalar e se balançar."));
        l.add(new Produto("Bebedouro para Aves", ACESSORIOS, "Agapornis", 12.90, 4.5, "Capacidade de 150 ml", null,
            "Bebedouro com tampa, que ajuda a manter a água limpa."));

        // ---------- Canários ----------
        l.add(new Produto("Mistura de Sementes para Canários", ALIMENTOS, "Canários", 18.90, 4.7, "Pacote de 500 g", null,
            "Mistura de alpiste e sementes, para a alimentação diária dos canários."));
        l.add(new Produto("Ração Extrusada para Canários", ALIMENTOS, "Canários", 26.90, 4.7, "Pacote de 350 g", null,
            "Ração balanceada, em pellets, que complementa a dieta."));
        l.add(new Produto("Gaiola Clássica de Arame", GAIOLAS, "Canários", 129.90, 4.6, "Altura de 45 cm", null,
            "Gaiola de arame, com bandeja removível e poleiros de madeira."));
        l.add(new Produto("Ninho de Fibra Natural", GAIOLAS, "Canários", 17.90, 4.6, "Diâmetro de 10 cm", null,
            "Ninho de fibras naturais, para o período de reprodução."));
        l.add(new Produto("Poleiros de Madeira Variados", ACESSORIOS, "Canários", 16.90, 4.5, "Kit com 4 unidades", null,
            "Poleiros de espessuras diferentes, para exercitar os pés."));
        l.add(new Produto("Banheira Acoplável", HIGIENE, "Canários", 21.90, 4.6, "Tamanho M", null,
            "Banheira que se prende à porta da gaiola, para o banho dos canários."));

        // ---------- Bettas ----------
        l.add(new Produto("Ração em Flocos para Bettas", ALIMENTOS, "Bettas", 16.90, 4.7, "Pote de 20 g", null,
            "Flocos formulados para peixes bettas, com alto teor de proteínas."));
        l.add(new Produto("Aquário Nano com Tampa e LED", GAIOLAS, "Bettas", 149.90, 4.7, "Capacidade de 10 L", null,
            "Aquário compacto, com tampa e luminária de LED, para um betta."));
        l.add(new Produto("Folha de Descanso para Bettas", BRINQUEDOS, "Bettas", 14.90, 4.5, "Fixação por ventosa", null,
            "Folha artificial macia, que permite ao peixe descansar perto da superfície."));
        l.add(new Produto("Planta Artificial de Seda", ACESSORIOS, "Bettas", 18.90, 4.6, "Altura de 20 cm", null,
            "Folhagem macia, que não machuca as nadadeiras longas."));
        l.add(new Produto("Termômetro Digital de Ventosa", ACESSORIOS, "Bettas", 24.90, 4.5, "Visor digital", null,
            "Termômetro de leitura rápida, para acompanhar a temperatura da água."));
        l.add(new Produto("Condicionador de Água Anticloro", HIGIENE, "Bettas", 19.90, 4.8, "Frasco de 60 ml", null,
            "Neutraliza o cloro e as cloraminas da água da torneira."));

        // ---------- Kinguios ----------
        l.add(new Produto("Ração em Pellets Flutuantes para Kinguios", ALIMENTOS, "Kinguios", 24.90, 4.7, "Pote de 100 g", null,
            "Pellets flutuantes, formulados para kinguios de todas as idades."));
        l.add(new Produto("Aquário Amplo de 120 Litros", GAIOLAS, "Kinguios", 599.00, 4.6, "Capacidade de 120 L", null,
            "Aquário espaçoso, indicado para kinguios, que crescem bastante."));
        l.add(new Produto("Caverna Decorativa de Resina", BRINQUEDOS, "Kinguios", 34.90, 4.5, "Tamanho M", null,
            "Decoração atóxica, que serve de esconderijo."));
        l.add(new Produto("Filtro Externo Silencioso", ACESSORIOS, "Kinguios", 189.90, 4.7, "Vazão de 800 L/h", null,
            "Filtro de baixo ruído, com boa vazão, para aquários de maior volume."));
        l.add(new Produto("Cascalho Natural Arredondado", ACESSORIOS, "Kinguios", 39.90, 4.5, "Pacote de 2 kg", null,
            "Cascalho de grãos arredondados, para o fundo do aquário."));
        l.add(new Produto("Teste de Qualidade da Água", HIGIENE, "Kinguios", 49.90, 4.7, "Kit com 25 tiras", null,
            "Tiras que medem parâmetros como pH e amônia, para acompanhar a água do aquário."));

        // ---------- Guppies ----------
        l.add(new Produto("Flocos para Peixes Ornamentais", ALIMENTOS, "Guppies", 17.90, 4.6, "Pote de 20 g", null,
            "Flocos nutritivos, que realçam as cores dos peixes ornamentais."));
        l.add(new Produto("Aquário Plantado Completo", GAIOLAS, "Guppies", 289.00, 4.7, "Capacidade de 40 L", null,
            "Aquário com tampa, luminária e espaço para plantas naturais."));
        l.add(new Produto("Rede de Maternidade para Filhotes", ACESSORIOS, "Guppies", 29.90, 4.4, "Tamanho P", null,
            "Separa os filhotes dos adultos, que podem comê-los."));
        l.add(new Produto("Aquecedor com Termostato", ACESSORIOS, "Guppies", 69.90, 4.6, "Potência de 50 W", null,
            "Aquecedor com termostato ajustável, para manter a água na temperatura certa."));
        l.add(new Produto("Biocondicionador para Aquários", HIGIENE, "Guppies", 22.90, 4.7, "Frasco de 100 ml", null,
            "Neutraliza o cloro e ajuda a deixar a água da torneira própria para o aquário."));

        // ---------- Neons ----------
        l.add(new Produto("Microflocos para Peixes de Cardume", ALIMENTOS, "Neons", 19.90, 4.7, "Pote de 20 g", null,
            "Flocos bem pequenos, ideais para peixes de boca pequena."));
        l.add(new Produto("Aquário Retangular com Tampa", GAIOLAS, "Neons", 399.00, 4.7, "Capacidade de 60 L", null,
            "Aquário retangular, com tampa, espaçoso para um cardume."));
        l.add(new Produto("Tronco Decorativo de Madeira Natural", BRINQUEDOS, "Neons", 44.90, 4.6, "Tamanho M", null,
            "Tronco natural para decorar e criar esconderijos."));
        l.add(new Produto("Luminária de LED Ajustável", ACESSORIOS, "Neons", 79.90, 4.6, "Para aquários de 50 a 70 cm", null,
            "Luminária de LED com ajuste de comprimento, indicada para aquários plantados."));
        l.add(new Produto("Substrato Fértil para Plantas", ACESSORIOS, "Neons", 59.90, 4.6, "Pacote de 3 kg", null,
            "Substrato rico em nutrientes, próprio para aquários plantados."));
        l.add(new Produto("Kit de Plantas Naturais", ACESSORIOS, "Neons", 49.90, 4.5, "Kit com 6 plantas", null,
            "Plantas aquáticas variadas, para decorar e criar abrigos."));

        return l;
    }


}
