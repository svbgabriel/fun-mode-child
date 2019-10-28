package br.anhembi.funmodechild.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.anhembi.funmodechild.models.Usuario;
import br.anhembi.funmodechild.services.ServiceUsuario;

@Controller
public class ContaController {

	@Autowired
	ServiceUsuario serviceUsuario;
	
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
	
	@PostMapping("/registration")
	public ModelAndView registrar(Usuario usuario) {
		ModelAndView mv = new ModelAndView();
		serviceUsuario.salvar(usuario);
		mv.setViewName("redirect:/login");
		return mv;
	}
}
