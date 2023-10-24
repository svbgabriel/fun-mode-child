package br.anhembi.funmodechild.repository;

import br.anhembi.funmodechild.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryPagamento extends JpaRepository<Pagamento, Long> {

}
