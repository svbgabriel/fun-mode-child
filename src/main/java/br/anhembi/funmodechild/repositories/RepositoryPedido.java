package br.anhembi.funmodechild.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.anhembi.funmodechild.models.Pedido;

public interface RepositoryPedido extends JpaRepository<Pedido, Long> {

}
