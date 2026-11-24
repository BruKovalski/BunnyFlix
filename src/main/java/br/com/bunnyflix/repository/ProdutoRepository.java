package br.com.bunnyflix.repository;

import br.com.bunnyflix.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByCategoria(String categoria);
    List<Produto> findByEspecie(String especie);
    Optional<Produto> findFirstByTitulo(String titulo);
}
