package br.anhembi.funmodechild.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.anhembi.funmodechild.models.Usuario;
import br.anhembi.funmodechild.repositories.RepositoryUsuario;
import br.anhembi.funmodechild.services.ServiceUsuario;

@Controller
public class ContaController {

	@Autowired
	ServiceUsuario serviceUsuario;
	
	@Autowired
	RepositoryUsuario repositoryUsuario;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/registration")
	public ModelAndView registrar() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cadastrar");
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	// TODO: Notificar que se o processo funcionou ou não
	@PostMapping("/registration")
	public ModelAndView registrar(Usuario usuario) {
		ModelAndView mv = new ModelAndView();
		serviceUsuario.salvar(usuario);
		mv.setViewName("redirect:/login");
		return mv;
	}
	
	@GetMapping("/conta")
	public ModelAndView conta() {		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("conta");
		return mv;
	}
	
	@PostMapping("/conta")
	public ModelAndView conta(@RequestParam(name = "passwordOld") String passwordOld, @RequestParam(name = "passwordNew") String passwordNew, @RequestParam(name = "passwordNewConfirm") String passwordNewConfirm, HttpServletRequest request) {		
		ModelAndView mv = new ModelAndView();
		// Checa se as novas senha são iguais
		Principal user = request.getUserPrincipal();
		
		if (passwordNew.equals(passwordNewConfirm)) {
			serviceUsuario.atualizar(user, passwordOld, passwordNew);
		} else {
			// TODO: Informar o erro ao usuário
		}
		mv.setViewName("redirect:/conta");
		return mv;
	}
	
	
}
