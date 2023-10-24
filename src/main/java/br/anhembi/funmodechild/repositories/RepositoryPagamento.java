package br.anhembi.funmodechild.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.anhembi.funmodechild.models.Pagamento;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryPagamento extends JpaRepository<Pagamento, Long> {

}
