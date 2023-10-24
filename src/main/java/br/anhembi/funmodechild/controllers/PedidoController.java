package br.anhembi.funmodechild.controllers;

import br.anhembi.funmodechild.models.Usuario;
import br.anhembi.funmodechild.repositories.RepositoryPedido;
import br.anhembi.funmodechild.repositories.RepositoryUsuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class PedidoController {

    private final RepositoryUsuario repositoryUsuario;
    private final RepositoryPedido repositoryPedido;

    public PedidoController(RepositoryUsuario repositoryUsuario, RepositoryPedido repositoryPedido) {
        this.repositoryUsuario = repositoryUsuario;
        this.repositoryPedido = repositoryPedido;
    }

    @GetMapping("/pedido")
    public ModelAndView pedido(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        // Recupera os dados do usuário
        Principal user = request.getUserPrincipal();
        Usuario usuario = repositoryUsuario.findByEmail(user.getName());

        mv.setViewName("pedido");
        mv.addObject("pedidos", repositoryPedido.findByUsuario(usuario.getId()));
        return mv;
    }
}
