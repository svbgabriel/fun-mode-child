package br.anhembi.funmodechild.repository;

import br.anhembi.funmodechild.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByCustomerId(String customerId);

    Optional<Order> findByIdAndCustomerId(String id, String customerId);
}
