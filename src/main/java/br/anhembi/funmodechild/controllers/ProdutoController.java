package br.anhembi.funmodechild.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.anhembi.funmodechild.models.Produto;
import br.anhembi.funmodechild.repositories.RepositoryProduto;

@Controller
public class ProdutoController {

	@Autowired
	RepositoryProduto repositoryProduto;
	
	@GetMapping("/produto/{id}")
	public ModelAndView produto(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView();
		Produto produto = repositoryProduto.getOne(id);
		mv.addObject("produto", produto);
		mv.addObject("produtosCategoria", repositoryProduto.findByCategoria(produto.getCategoria().getId()));
		mv.setViewName("produto");
		return mv;
	}
}
