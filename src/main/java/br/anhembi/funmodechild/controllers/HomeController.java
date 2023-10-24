package br.anhembi.funmodechild.controllers;

import br.anhembi.funmodechild.repositories.RepositoryCategoria;
import br.anhembi.funmodechild.repositories.RepositoryProduto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final RepositoryCategoria repositoryCategoria;
    private final RepositoryProduto repositoryProduto;

    public HomeController(RepositoryCategoria repositoryCategoria, RepositoryProduto repositoryProduto) {
        this.repositoryCategoria = repositoryCategoria;
        this.repositoryProduto = repositoryProduto;
    }

    @GetMapping("/")
    public ModelAndView home(@RequestParam(name = "cat", defaultValue = "0") Long catId) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("categorias", repositoryCategoria.findAll());
        mv.addObject("produtosPromovidos", repositoryProduto.findPromovidos());
        if (catId == 0) {
            mv.addObject("produtos", repositoryProduto.findAll());
        } else {
            mv.addObject("produtos", repositoryProduto.findByCategoria(catId));
        }
        mv.setViewName("home");
        return mv;
    }
}
