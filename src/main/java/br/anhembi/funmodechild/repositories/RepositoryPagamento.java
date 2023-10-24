package br.anhembi.funmodechild.repositories;

import br.anhembi.funmodechild.models.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryPagamento extends JpaRepository<Pagamento, Long> {

}
