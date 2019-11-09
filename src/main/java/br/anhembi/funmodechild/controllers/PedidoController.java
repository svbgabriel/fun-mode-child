package br.anhembi.funmodechild.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.anhembi.funmodechild.models.Usuario;
import br.anhembi.funmodechild.repositories.RepositoryPedido;
import br.anhembi.funmodechild.repositories.RepositoryUsuario;

@Controller
public class PedidoController {

	@Autowired
	RepositoryUsuario repositoryUsuario;

	@Autowired
	RepositoryPedido repositoryPedido;

	@GetMapping("/pedido")
	public ModelAndView pedido(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		// Recupera os dados do usuário
		Principal user = request.getUserPrincipal();
		Usuario usuario = repositoryUsuario.findByEmail(user.getName());
		
		// TODO: Usar o status do pedido

		mv.setViewName("pedido");
		mv.addObject("pedidos", repositoryPedido.findByUsuario(usuario.getId()));
		return mv;
	}
}
