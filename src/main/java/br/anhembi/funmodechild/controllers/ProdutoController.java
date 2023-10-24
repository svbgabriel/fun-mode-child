package br.anhembi.funmodechild.controllers;

import br.anhembi.funmodechild.models.Produto;
import br.anhembi.funmodechild.repositories.RepositoryProduto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProdutoController {

    private final RepositoryProduto repositoryProduto;

    public ProdutoController(RepositoryProduto repositoryProduto) {
        this.repositoryProduto = repositoryProduto;
    }

    @GetMapping("/produto/{id}")
    public ModelAndView produto(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView();
        Produto produto = repositoryProduto.getReferenceById(id);
        mv.addObject("produto", produto);
        mv.addObject("produtosCategoria", repositoryProduto.findByCategoria(produto.getCategoria().getId()));
        mv.setViewName("produto");
        return mv;
    }
}
