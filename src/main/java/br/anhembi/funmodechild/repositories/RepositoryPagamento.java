package br.anhembi.funmodechild.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.anhembi.funmodechild.models.Pagamento;

public interface RepositoryPagamento extends JpaRepository<Pagamento, Long> {

}
