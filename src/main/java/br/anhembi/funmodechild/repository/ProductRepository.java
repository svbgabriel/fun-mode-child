package br.anhembi.funmodechild.repository;

import br.anhembi.funmodechild.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByPromotedIsTrue();

    List<Product> findByCategoryId(String categoryId);

    Optional<Product> findBySku(long sku);
}
