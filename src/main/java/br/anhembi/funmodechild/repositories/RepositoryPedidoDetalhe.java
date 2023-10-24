package br.anhembi.funmodechild.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.anhembi.funmodechild.models.PedidoDetalhe;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryPedidoDetalhe extends JpaRepository<PedidoDetalhe, Long> {

    @Query("SELECT d FROM PedidoDetalhe d INNER JOIN d.pedido p WHERE p.id = :pedidoId AND p.usuario.id = :usuarioId")
    List<PedidoDetalhe> findByPedido(@Param("pedidoId") long pedidoId, @Param("usuarioId") long usuarioId);
}
