package br.anhembi.funmodechild.repository;

import br.anhembi.funmodechild.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositoryProduto extends JpaRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p WHERE p.promovido = true")
    List<Produto> findPromovidos();

    @Query("SELECT p FROM Produto p INNER JOIN p.categoria c WHERE c.id = :id")
    List<Produto> findByCategoria(@Param("id") long id);

    Optional<Produto> findBySku(long sku);
}
