package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Product;
import br.anhembi.funmodechild.model.response.ProductResponse;
import br.anhembi.funmodechild.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getProductsByCategory(String categoryId) {
        return productRepository.findByCategoryId(categoryId)
            .stream().map(Product::toApiResponse)
            .toList();
    }

    public List<ProductResponse> getPromotedProducts() {
        return productRepository.findByPromotedIsTrue()
            .stream().map(Product::toApiResponse)
            .toList();
    }

    public ProductResponse getProductById(String id) {
        return productRepository.findById(id).orElseThrow().toApiResponse();
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll()
            .stream().map(Product::toApiResponse)
            .toList();
    }
}
