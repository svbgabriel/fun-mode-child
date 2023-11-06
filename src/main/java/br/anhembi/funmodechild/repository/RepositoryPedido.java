package br.anhembi.funmodechild.repository;

import br.anhembi.funmodechild.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryPedido extends JpaRepository<Pedido, Long> {

    @Query("SELECT p FROM Pedido p INNER JOIN p.usuario u WHERE u.id = :id")
    List<Pedido> findByUsuario(@Param("id") long id);

    @Query("SELECT p FROM Pedido p INNER JOIN p.usuario u WHERE u.id = :userId AND p.id = :pedidoId")
    Pedido findByUsuarioAndId(@Param("userId") long userId, @Param("pedidoId") long pedidoId);
}
