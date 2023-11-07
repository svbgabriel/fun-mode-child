package br.anhembi.funmodechild.controller;

import br.anhembi.funmodechild.entity.Usuario;
import br.anhembi.funmodechild.service.OrderService;
import br.anhembi.funmodechild.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    public OrderController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/pedido")
    public ModelAndView pedido(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        // Recupera os dados do usuário
        Usuario usuario = userService.getLoggedUser(request);

        mv.setViewName("pedido");
        mv.addObject("pedidos", orderService.getOrdersByUserId(usuario.getId()));
        return mv;
    }

    @GetMapping("/pedido/{id}/detalhe")
    public ModelAndView detalhe(@PathVariable("id") Long id, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        // Recupera os dados do usuário
        Usuario usuario = userService.getLoggedUser(request);

        mv.addObject("pedido", orderService.getOrderById(id));
        mv.addObject("detalhes", orderService.getOrderDetailsByOrderId(id, usuario.getId()));
        mv.addObject("pedidoId", id);
        mv.setViewName("detalhe");
        return mv;
    }

    @GetMapping("/pedido/{id}/cancelar")
    public ModelAndView cancelar(@PathVariable("id") Long id, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        // Recupera os dados do usuário
        Usuario usuario = userService.getLoggedUser(request);

        orderService.updateStatus(id, usuario.getId(), false);

        mv.addObject("pedidoId", id);
        mv.setViewName("cancelar");
        return mv;
    }
}
