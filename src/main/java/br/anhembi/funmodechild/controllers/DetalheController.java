package br.anhembi.funmodechild.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.anhembi.funmodechild.repositories.RepositoryPedidoDetalhe;

@Controller
public class DetalheController {

	@Autowired
	RepositoryPedidoDetalhe repositoryPedidoDetalhe;

	@GetMapping("/pedido/{id}/detalhe")
	public ModelAndView detalhe(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("detalhes", repositoryPedidoDetalhe.findByPedido(id));
		mv.addObject("pedidoId", id);
		mv.setViewName("detalhe");
		return mv;
	}
}
