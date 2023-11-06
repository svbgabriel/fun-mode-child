package br.anhembi.funmodechild.repository;

import br.anhembi.funmodechild.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Pagamento, Long> {

}
