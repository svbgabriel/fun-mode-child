package br.anhembi.funmodechild.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.anhembi.funmodechild.models.Pedido;
import br.anhembi.funmodechild.models.Usuario;
import br.anhembi.funmodechild.repositories.RepositoryPedido;
import br.anhembi.funmodechild.repositories.RepositoryUsuario;
import br.anhembi.funmodechild.services.ServicePedido;

@Controller
public class CancelarController {

	@Autowired
	RepositoryPedido repositoryPedido;
	
	@Autowired
	RepositoryUsuario repositoryUsuario;
	
	@Autowired
	ServicePedido servicePedido;
	
	@GetMapping("/pedido/{id}/cancelar")
	public ModelAndView cancelar(@PathVariable("id") Long id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		// Recupera os dados do usuário
		Principal user = request.getUserPrincipal();
		Usuario usuario = repositoryUsuario.findByEmail(user.getName());

		/*
	    String p_id = request.getParameter("pedido_id");
	    // Tenta converter o id recebido para Integer.
	    int ip_id = 0;
	        try {
	            ip_id = Integer.parseInt(p_id);
	        } catch (NumberFormatException nfe) {

	        }
	        
	    String user = (String)session.getAttribute("username");
	    
	    PedidosDao pedidodao = new PedidosDao();
	    Pedidos pedido = pedidodao.consultarUsuarioPedido(user, ip_id);
	    Detalhe_PedidosDao dpedidos = new Detalhe_PedidosDao();
	    
	    if (pedido.getUsuario() != null) {        
	        
	        dpedidos.remover(ip_id);
	        pedidodao.remover(pedido);
	    } else {
	    }
		*/
		
		Pedido pedido = repositoryPedido.findByUsuarioAndId(usuario.getId(), id);
		servicePedido.atualizarStatus(pedido, false);
		
	    mv.addObject("pedido", pedido);
		mv.setViewName("cancelar");
		return mv;
	}
}
