package br.anhembi.funmodechild.repository;

import br.anhembi.funmodechild.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositoryProduto extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.promovido = true")
    List<Product> findPromovidos();

    @Query("SELECT p FROM Product p INNER JOIN p.categoria c WHERE c.id = :id")
    List<Product> findByCategoria(@Param("id") long id);

    Optional<Product> findBySku(long sku);
}
