package br.anhembi.funmodechild.controller;

import br.anhembi.funmodechild.entity.Product;
import br.anhembi.funmodechild.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProdutoController {

    private final ProductService productService;

    public ProdutoController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public ModelAndView produto(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        Product product = productService.getProductById(id);
        mv.addObject("product", product);
        mv.addObject("produtosCategoria", productService.getProducts(product.getCategoria().getId()));
        mv.setViewName("produto");
        return mv;
    }
}
