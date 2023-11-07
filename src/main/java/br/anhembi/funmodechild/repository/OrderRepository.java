package br.anhembi.funmodechild.repository;

import br.anhembi.funmodechild.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT p FROM Order p INNER JOIN p.customer u WHERE u.id = :id")
    List<Order> findByUsuario(@Param("id") long id);

    @Query("SELECT p FROM Order p INNER JOIN p.customer u WHERE u.id = :userId AND p.id = :pedidoId")
    Order findByUsuarioAndId(@Param("userId") long userId, @Param("pedidoId") long pedidoId);
}
