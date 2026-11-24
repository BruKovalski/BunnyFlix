package br.com.bunnyflix.service;

import br.com.bunnyflix.catalogo.CatalogoCategorias;
import br.com.bunnyflix.catalogo.CatalogoEspecies;
import br.com.bunnyflix.catalogo.CategoriaProduto;
import br.com.bunnyflix.catalogo.Especie;
import br.com.bunnyflix.catalogo.ResultadoCatalogo;
import br.com.bunnyflix.model.Produto;
import br.com.bunnyflix.repository.ProdutoRepository;
import br.com.bunnyflix.util.Slug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Consultas do catálogo. Somente leitura: a loja não cria pedidos nem altera produtos. */
@Service
public class ProdutoService {

    public static final int TAMANHO_PAGINA = 12;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public List<Produto> buscarPorCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    public List<Produto> buscarPorEspecie(String especie) {
        return produtoRepository.findByEspecie(especie);
    }

    public Optional<Produto> buscarPorTitulo(String titulo) {
        return produtoRepository.findFirstByTitulo(titulo);
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    /** Produtos escolhidos pela loja, na ordem informada. Títulos inexistentes são ignorados. */
    public List<Produto> selecionar(List<String> titulos) {
        List<Produto> lista = new ArrayList<>();
        for (String titulo : titulos) {
            buscarPorTitulo(titulo).ifPresent(lista::add);
        }
        return lista;
    }

    /** Outros produtos da mesma espécie, priorizando tipos diferentes do produto atual. */
    public List<Produto> relacionados(Produto produto, int limite) {
        return produtoRepository.findByEspecie(produto.getEspecie()).stream()
            .filter(p -> !p.getId().equals(produto.getId()))
            .sorted(Comparator
                .comparing((Produto p) -> p.getCategoria() != null && p.getCategoria().equals(produto.getCategoria()))
                .thenComparing(p -> Slug.semAcentos(p.getTitulo())))
            .limit(limite)
            .toList();
    }

    /**
     * Pesquisa com filtros combináveis. Parâmetros nulos são ignorados.
     * Ordens aceitas: "nome", "preco-asc" e "preco-desc"; sem ordem, as espécies são intercaladas.
     */
    public ResultadoCatalogo pesquisar(String grupo, String especie, String categoria, String busca,
                                       Double precoMin, Double precoMax, String ordem, int pagina) {
        String[] termos = busca == null ? new String[0] : Slug.semAcentos(busca).trim().split("\\s+");

        List<Produto> lista = new ArrayList<>(produtoRepository.findAll().stream()
            .filter(p -> especie == null || especie.equals(p.getEspecie()))
            .filter(p -> grupo == null || grupo.equals(grupoDe(p)))
            .filter(p -> categoria == null || categoria.equals(p.getCategoria()))
            .filter(p -> precoMin == null || (p.getPreco() != null && p.getPreco() >= precoMin))
            .filter(p -> precoMax == null || (p.getPreco() != null && p.getPreco() <= precoMax))
            .filter(p -> combina(p, termos))
            .toList());

        if ("nome".equals(ordem)) {
            lista.sort(Comparator.comparing(p -> Slug.semAcentos(p.getTitulo())));
        } else if ("preco-asc".equals(ordem)) {
            lista.sort(Comparator.comparing(Produto::getPreco, Comparator.nullsLast(Comparator.naturalOrder())));
        } else if ("preco-desc".equals(ordem)) {
            lista.sort(Comparator.comparing(Produto::getPreco, Comparator.nullsLast(Comparator.reverseOrder())));
        } else {
            lista = intercalarPorEspecie(lista);
        }

        int total = lista.size();
        int totalPaginas = Math.max(1, (int) Math.ceil(total / (double) TAMANHO_PAGINA));
        int atual = Math.min(Math.max(pagina, 1), totalPaginas);
        int inicio = (atual - 1) * TAMANHO_PAGINA;
        List<Produto> itens = lista.subList(Math.min(inicio, total), Math.min(inicio + TAMANHO_PAGINA, total));
        return new ResultadoCatalogo(itens, total, atual, totalPaginas);
    }

    private static String grupoDe(Produto p) {
        Especie e = CatalogoEspecies.buscar(p.getEspecie());
        return e == null ? null : e.getGrupo().getNome();
    }

    /** Todos os termos digitados precisam aparecer em algum campo do produto (sem distinguir acentos). */
    private static boolean combina(Produto p, String[] termos) {
        if (termos.length == 0 || (termos.length == 1 && termos[0].isEmpty())) {
            return true;
        }
        Especie e = CatalogoEspecies.buscar(p.getEspecie());
        CategoriaProduto c = CatalogoCategorias.porValor().get(p.getCategoria());
        String texto = Slug.semAcentos(String.join(" ",
            nulo(p.getTitulo()), nulo(p.getCategoria()), c != null ? c.getRotulo() : "",
            nulo(p.getEspecie()), e != null ? e.getGrupo().getNome() : "",
            e != null ? nulo(e.getCientifico()) : "", nulo(p.getDescricao()), nulo(p.getMedida())));
        for (String termo : termos) {
            if (!texto.contains(termo)) {
                return false;
            }
        }
        return true;
    }

    private static String nulo(String texto) {
        return texto == null ? "" : texto;
    }

    /** Alterna as espécies para que as primeiras páginas mostrem variedade. */
    private static List<Produto> intercalarPorEspecie(List<Produto> produtos) {
        Map<String, Deque<Produto>> filas = new LinkedHashMap<>();
        for (Produto p : produtos) {
            filas.computeIfAbsent(String.valueOf(p.getEspecie()), chave -> new ArrayDeque<>()).add(p);
        }
        List<Produto> resultado = new ArrayList<>(produtos.size());
        while (resultado.size() < produtos.size()) {
            for (Deque<Produto> fila : filas.values()) {
                Produto p = fila.poll();
                if (p != null) {
                    resultado.add(p);
                }
            }
        }
        return resultado;
    }
}
