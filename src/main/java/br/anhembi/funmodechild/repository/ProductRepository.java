package br.anhembi.funmodechild.repository;

import br.anhembi.funmodechild.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByPromotedIsTrue();

    List<Product> findByCategoryId(String categoryId);
}
