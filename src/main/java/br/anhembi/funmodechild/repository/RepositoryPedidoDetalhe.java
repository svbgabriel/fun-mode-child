package br.anhembi.funmodechild.repository;

import br.anhembi.funmodechild.model.PedidoDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryPedidoDetalhe extends JpaRepository<PedidoDetalhe, Long> {

    @Query("SELECT d FROM PedidoDetalhe d INNER JOIN d.pedido p WHERE p.id = :pedidoId AND p.usuario.id = :usuarioId")
    List<PedidoDetalhe> findByPedido(@Param("pedidoId") long pedidoId, @Param("usuarioId") long usuarioId);
}