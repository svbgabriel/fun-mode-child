package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Product;
import br.anhembi.funmodechild.repository.RepositoryProduto;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final RepositoryProduto repositoryProduto;

    public ProductService(RepositoryProduto repositoryProduto) {
        this.repositoryProduto = repositoryProduto;
    }

    public List<Product> getProducts(@Nullable Long id) {
        if (id == null) {
            return repositoryProduto.findAll();
        }

        return repositoryProduto.findByCategoria(id);
    }

    public List<Product> getPromotedProducts() {
        return repositoryProduto.findByPromovidoIsTrue();
    }

    public Product getProductById(long id) {
        return repositoryProduto.getReferenceById(id);
    }
}
