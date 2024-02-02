package br.anhembi.funmodechild.controller;

import br.anhembi.funmodechild.model.response.ProductResponse;
import br.anhembi.funmodechild.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public ProductResponse getProductById(@PathVariable("productId") String id) {
        return productService.findProductById(id);
    }

    @GetMapping("/promoted")
    public List<ProductResponse> getPromotedProducts() {
        return productService.listPromotedProducts();
    }

    @GetMapping
    public List<ProductResponse> getProducts(@RequestParam(value = "categoryId", required = false) String categoryId) {
        if (categoryId != null) {
            return productService.listProductsByCategory(categoryId);
        }

        return productService.listProducts();
    }
}
