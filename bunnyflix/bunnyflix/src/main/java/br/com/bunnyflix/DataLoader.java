package br.com.bunnyflix;

import br.com.bunnyflix.model.Produto;
import br.com.bunnyflix.model.Usuario;
import br.com.bunnyflix.repository.ProdutoRepository;
import br.com.bunnyflix.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ProdutoRepository produtoRepo, UsuarioRepository usuarioRepo) {
        return args -> {
            if (usuarioRepo.count() == 0) {
                usuarioRepo.save(new Usuario("tricolor", "1903", "Gremista Tricolor"));
                usuarioRepo.save(new Usuario("gremio", "1903", "Grêmio FBPA"));
            }

            if (produtoRepo.count() == 0) {
                produtoRepo.save(new Produto("Hay Premium de Timóteo Sabor Premium", "Alimentos", "Coelhos", 49.90, 4.9, "Paco 1kg", "https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=600&q=80", "O feno de Timóteo supremo selecionado a mão para a saúde digestiva e desgaste dental ideal do seu coelhinho e cavias."));
                produtoRepo.save(new Produto("Ração Super Premium Gourmet Roedores", "Alimentos", "Porquinhos-da-Índia", 35.50, 4.8, "Embalagem 800g", "https://images.unsplash.com/photo-1548767797-d8c844163c4c?auto=format&fit=crop&w=600&q=80", "Nutrição enriquecida com Vitamina C estabilizada, sem corantes artificiais e com alta digestibilidade."));
                produtoRepo.save(new Produto("Petisco Crocante de Maçã & Cenoura", "Alimentos", "Hamsters", 18.90, 4.7, "Snack 150g", "https://images.unsplash.com/photo-1425082661705-1834bfd09dca?auto=format&fit=crop&w=600&q=80", "Deliciosos biscoitinhos assados e naturais para enriquecimento alimentar e recompensa em treinos."));
                produtoRepo.save(new Produto("Extrusado Especial Chinchila Supreme", "Alimentos", "Chinchilas", 52.00, 5.0, "Saco 1.2kg", "https://images.unsplash.com/photo-1535268647677-300dbf3d78d1?auto=format&fit=crop&w=600&q=80", "Fórmula equilibrada rica em fibras de alfafa e minerais essenciais para pelagens brilhantes."));

                produtoRepo.save(new Produto("Mansão Deluxe em Madeira Maciça", "Gaiolas", "Coelhos", 389.00, 4.9, "2 Andares - 120cm", "https://images.unsplash.com/photo-1518791841217-8f162f1e1131?auto=format&fit=crop&w=600&q=80", "Habitat espaçoso com rampas antiderrapantes, bandeja removível para higienização rápida e acabamento atóxico."));
                produtoRepo.save(new Produto("Gaiola Panoramic Glass View", "Gaiolas", "Hamsters", 210.00, 4.6, "Compacta Deluxe", "https://images.unsplash.com/photo-1548767797-d8c844163c4c?auto=format&fit=crop&w=600&q=80", "Gaiola panorâmica transparente com ventilação otimizada, prevenindo fugas e permitindo visão 360."));
                produtoRepo.save(new Produto("Toca Estilo Cabana Escandinava", "Gaiolas", "Porquinhos-da-Índia", 89.90, 4.8, "Tamanho G", "https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=600&q=80", "Refúgio aconchegante acolchoado de tecido lavável para momentos de descanso com total segurança."));
                produtoRepo.save(new Produto("Playground Suspenso com Pontes", "Gaiolas", "Chinchilas", 145.00, 4.7, "Kit 5 Peças", "https://images.unsplash.com/photo-1425082661705-1834bfd09dca?auto=format&fit=crop&w=600&q=80", "Estruturas moduladas de madeira natural para fixar nas grades e incentivar saltos e escaladas."));

                produtoRepo.save(new Produto("Roda Silenciosa Turbo Spinner 28cm", "Brinquedos", "Hamsters", 79.90, 5.0, "Diâmetro 28cm", "https://images.unsplash.com/photo-1425082661705-1834bfd09dca?auto=format&fit=crop&w=600&q=80", "Rolamento duplo ultrassilencioso que previne curvatura na coluna do seu pequeno roedor."));
                produtoRepo.save(new Produto("Túnel Flexível de Madeira & Salgueiro", "Brinquedos", "Coelhos", 64.90, 4.8, "Extensível 50cm", "https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=600&q=80", "Túnel moldável de galhos comestíveis ideal para roer, esconder e gastar energia no dia a dia."));
                produtoRepo.save(new Produto("Bola de Erva-dos-Prados com Guizo", "Brinquedos", "Porquinhos-da-Índia", 24.90, 4.5, "Diâmetro 12cm", "https://images.unsplash.com/photo-1548767797-d8c844163c4c?auto=format&fit=crop&w=600&q=80", "Brinquedo interativo 100% natural que estimula o exercício físico e combate o estresse."));
                produtoRepo.save(new Produto("Cenourinhas Morderas de Salgueiro", "Brinquedos", "Coelhos", 29.90, 4.9, "Pack com 3 un", "https://images.unsplash.com/photo-1535268647677-300dbf3d78d1?auto=format&fit=crop&w=600&q=80", "Mordedores divertidos em formato de cenoura feitos com fibras atóxicas para desgaste dentes."));

                produtoRepo.save(new Produto("Bebedouro Automático Anti-Vazamento", "Acessórios", "Coelhos", 42.00, 4.7, "Capacidade 500ml", "https://images.unsplash.com/photo-1585110396000-c9ffd4e4b308?auto=format&fit=crop&w=600&q=80", "Bico duplo em aço inox de alta durabilidade com presilha universal ajustável para gaiolas."));
                produtoRepo.save(new Produto("Porta Feno Suspenso em Metal", "Acessórios", "Porquinhos-da-Índia", 38.90, 4.6, "Suporte Duplo", "https://images.unsplash.com/photo-1548767797-d8c844163c4c?auto=format&fit=crop&w=600&q=80", "Mantenha o feno limpo e livre de sujeiras na gaiola com este suporte inteligente."));
                produtoRepo.save(new Produto("Banheiro de Areia Vulcânica", "Acessórios", "Chinchilas", 59.90, 4.9, "Kit Banho de Pó", "https://images.unsplash.com/photo-1535268647677-300dbf3d78d1?auto=format&fit=crop&w=600&q=80", "Recipiente fechado que evita sujeira externa, ideal para o banho seco essencial das chinchilas."));
                produtoRepo.save(new Produto("Escova de Pelo Cerdas Macias Soft", "Acessórios", "Coelhos", 32.50, 4.8, "Ergonomica", "https://images.unsplash.com/photo-1425082661705-1834bfd09dca?auto=format&fit=crop&w=600&q=80", "Remove pelos mortos delicadamente sem machucar a pele sensível dos pequenos animais."));
            }
        };
    }
}
