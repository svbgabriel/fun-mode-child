package br.anhembi.funmodechild.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.anhembi.funmodechild.models.PedidoDetalhe;

public interface RepositoryPedidoDetalhe extends JpaRepository<PedidoDetalhe, Long> {

	// TODO: Recuperar somente os pedidos do usuário logo
	@Query("SELECT d FROM PedidoDetalhe d INNER JOIN d.pedido p WHERE p.id = :id")
	List<PedidoDetalhe> findByPedido(@Param("id") long id);
}
