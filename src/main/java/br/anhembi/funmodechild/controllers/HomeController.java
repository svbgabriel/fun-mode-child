package br.anhembi.funmodechild.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.anhembi.funmodechild.repositories.RepositoryCategoria;
import br.anhembi.funmodechild.repositories.RepositoryProduto;

@Controller
public class HomeController {

	@Autowired
	RepositoryCategoria repositoryCategoria;
	@Autowired
	RepositoryProduto repositoryProduto;

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
