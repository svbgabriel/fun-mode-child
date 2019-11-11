package br.anhembi.funmodechild.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.anhembi.funmodechild.models.Usuario;
import br.anhembi.funmodechild.repositories.RepositoryPedido;
import br.anhembi.funmodechild.repositories.RepositoryPedidoDetalhe;
import br.anhembi.funmodechild.repositories.RepositoryUsuario;

@Controller
public class DetalheController {

	@Autowired
	RepositoryPedidoDetalhe repositoryPedidoDetalhe;

	@Autowired
	RepositoryPedido repositoryPedido;
	
	@Autowired
	RepositoryUsuario repositoryUsuario;

	@GetMapping("/pedido/{id}/detalhe")
	public ModelAndView detalhe(@PathVariable("id") Long id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		// Recupera os dados do usuário
		Principal user = request.getUserPrincipal();
		Usuario usuario = repositoryUsuario.findByEmail(user.getName());

		mv.addObject("pedido", repositoryPedido.getOne(id));
		mv.addObject("detalhes", repositoryPedidoDetalhe.findByPedido(id, usuario.getId()));
		mv.addObject("pedidoId", id);
		mv.setViewName("detalhe");
		return mv;
	}
}
