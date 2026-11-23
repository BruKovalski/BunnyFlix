package br.com.bunnyflix.repository;

import br.com.bunnyflix.model.LogAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogAcessoRepository extends JpaRepository<LogAcesso, Long> {
    List<LogAcesso> findTop10ByOrderByIdDesc();
}
