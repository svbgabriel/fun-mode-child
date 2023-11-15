package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Product;
import br.anhembi.funmodechild.repository.ProductRepository;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts(@Nullable String categoryId) {
        if (categoryId == null) {
            return productRepository.findAll();
        }

        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getPromotedProducts() {
        return productRepository.findByPromotedIsTrue();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElseThrow();
    }
}
