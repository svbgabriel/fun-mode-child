package br.anhembi.funmodechild.controller;

import br.anhembi.funmodechild.service.CategoryService;
import br.anhembi.funmodechild.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public HomeController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/")
    public ModelAndView home(@RequestParam(name = "cat", required = false) Long catId) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("categories", categoryService.getAll());
        mv.addObject("promotedList", productService.getPromotedProducts());
        mv.addObject("products", productService.getProducts(catId));
        mv.setViewName("home");
        return mv;
    }
}
