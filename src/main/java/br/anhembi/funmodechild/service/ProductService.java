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

    public List<Product> getProducts(@Nullable Long id) {
        if (id == null) {
            return productRepository.findAll();
        }

        return productRepository.findByCategoria(id);
    }

    public List<Product> getPromotedProducts() {
        return productRepository.findByPromovidoIsTrue();
    }

    public Product getProductById(long id) {
        return productRepository.getReferenceById(id);
    }
}
