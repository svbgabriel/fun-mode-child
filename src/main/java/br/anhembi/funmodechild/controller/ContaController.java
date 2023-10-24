package br.anhembi.funmodechild.controller;

import br.anhembi.funmodechild.model.Carrinho;
import br.anhembi.funmodechild.model.Usuario;
import br.anhembi.funmodechild.service.ServiceUsuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class ContaController {

    private final ServiceUsuario serviceUsuario;

    public ContaController(ServiceUsuario serviceUsuario) {
        this.serviceUsuario = serviceUsuario;
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        session.setAttribute("carrinhocompras", new Carrinho());
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
    public ModelAndView conta(@RequestParam(name = "passwordOld") String passwordOld,
                              @RequestParam(name = "passwordNew") String passwordNew,
                              @RequestParam(name = "passwordNewConfirm") String passwordNewConfirm,
                              HttpServletRequest request) {
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