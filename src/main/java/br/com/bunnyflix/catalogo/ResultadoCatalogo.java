package br.com.bunnyflix.catalogo;

import br.com.bunnyflix.model.Produto;

import java.util.List;
import java.util.stream.IntStream;

/** Uma página de resultados do catálogo, já filtrada e ordenada. */
public class ResultadoCatalogo {

    private final List<Produto> itens;
    private final int total;
    private final int pagina;
    private final int totalPaginas;

    public ResultadoCatalogo(List<Produto> itens, int total, int pagina, int totalPaginas) {
        this.itens = itens;
        this.total = total;
        this.pagina = pagina;
        this.totalPaginas = totalPaginas;
    }

    public List<Produto> getItens() { return itens; }
    public int getTotal() { return total; }
    public int getPagina() { return pagina; }
    public int getTotalPaginas() { return totalPaginas; }

    public List<Integer> getPaginas() {
        return IntStream.rangeClosed(1, totalPaginas).boxed().toList();
    }
}
