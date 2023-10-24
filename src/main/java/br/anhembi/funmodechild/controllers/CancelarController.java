package br.anhembi.funmodechild.controllers;

import br.anhembi.funmodechild.models.Pedido;
import br.anhembi.funmodechild.models.Usuario;
import br.anhembi.funmodechild.repositories.RepositoryPedido;
import br.anhembi.funmodechild.repositories.RepositoryUsuario;
import br.anhembi.funmodechild.services.ServicePedido;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class CancelarController {

    private final RepositoryPedido repositoryPedido;
    private final RepositoryUsuario repositoryUsuario;
    private final ServicePedido servicePedido;

    public CancelarController(RepositoryPedido repositoryPedido,
                              RepositoryUsuario repositoryUsuario,
                              ServicePedido servicePedido) {
        this.repositoryPedido = repositoryPedido;
        this.repositoryUsuario = repositoryUsuario;
        this.servicePedido = servicePedido;
    }

    @GetMapping("/pedido/{id}/cancelar")
    public ModelAndView cancelar(@PathVariable("id") Long id, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        // Recupera os dados do usuário
        Principal user = request.getUserPrincipal();
        Usuario usuario = repositoryUsuario.findByEmail(user.getName());

        Pedido pedido = repositoryPedido.findByUsuarioAndId(usuario.getId(), id);
        servicePedido.atualizarStatus(pedido, false);

        mv.addObject("pedido", pedido);
        mv.setViewName("cancelar");
        return mv;
    }
}
