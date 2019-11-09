package br.anhembi.funmodechild.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.anhembi.funmodechild.models.Pedido;

public interface RepositoryPedido extends JpaRepository<Pedido, Long> {

	@Query("SELECT p FROM Pedido p INNER JOIN p.usuario u WHERE u.id = :id")
	List<Pedido> findByUsuario(@Param("id") long id);
}
