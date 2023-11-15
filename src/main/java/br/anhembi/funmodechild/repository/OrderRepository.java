package br.anhembi.funmodechild.repository;

import br.anhembi.funmodechild.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByCustomerId(String customerId);

    Order findByIdAndCustomerId(String id, String customerId);
}
