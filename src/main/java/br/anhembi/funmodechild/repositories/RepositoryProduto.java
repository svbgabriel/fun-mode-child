package br.anhembi.funmodechild.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.anhembi.funmodechild.models.Produto;

public interface RepositoryProduto extends JpaRepository<Produto, Long> {

	@Query("SELECT p FROM Produto p WHERE p.promovido = true")
	List<Produto> findPromovidos();
	
	@Query("SELECT p FROM Produto p INNER JOIN p.categoria c WHERE c.id = :id")
	List<Produto> findByCategoria(@Param("id") long id);
}
