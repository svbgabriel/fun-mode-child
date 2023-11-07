package br.anhembi.funmodechild.repository;

import br.anhembi.funmodechild.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("SELECT d FROM OrderDetail d INNER JOIN d.order p WHERE p.id = :pedidoId AND p.customer.id = :usuarioId")
    List<OrderDetail> findByPedido(@Param("pedidoId") long pedidoId, @Param("usuarioId") long usuarioId);
}
