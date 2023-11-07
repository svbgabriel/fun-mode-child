package br.anhembi.funmodechild.repository;

import br.anhembi.funmodechild.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Customer, Long> {

    Customer findByEmail(String email);
}
