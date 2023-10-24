package br.anhembi.funmodechild.controller;

import br.anhembi.funmodechild.model.Usuario;
import br.anhembi.funmodechild.repository.RepositoryPedido;
import br.anhembi.funmodechild.repository.RepositoryPedidoDetalhe;
import br.anhembi.funmodechild.repository.RepositoryUsuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class DetalheController {

    private final RepositoryPedidoDetalhe repositoryPedidoDetalhe;
    private final RepositoryPedido repositoryPedido;
    private final RepositoryUsuario repositoryUsuario;

    public DetalheController(RepositoryPedidoDetalhe repositoryPedidoDetalhe,
                             RepositoryPedido repositoryPedido,
                             RepositoryUsuario repositoryUsuario) {
        this.repositoryPedidoDetalhe = repositoryPedidoDetalhe;
        this.repositoryPedido = repositoryPedido;
        this.repositoryUsuario = repositoryUsuario;
    }

    @GetMapping("/pedido/{id}/detalhe")
    public ModelAndView detalhe(@PathVariable("id") Long id, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        // Recupera os dados do usuário
        Principal user = request.getUserPrincipal();
        Usuario usuario = repositoryUsuario.findByEmail(user.getName());

        mv.addObject("pedido", repositoryPedido.getReferenceById(id));
        mv.addObject("detalhes", repositoryPedidoDetalhe.findByPedido(id, usuario.getId()));
        mv.addObject("pedidoId", id);
        mv.setViewName("detalhe");
        return mv;
    }
}
