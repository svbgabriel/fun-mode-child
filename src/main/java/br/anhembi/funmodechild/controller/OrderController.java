package br.anhembi.funmodechild.controller;

import br.anhembi.funmodechild.entity.Customer;
import br.anhembi.funmodechild.entity.Order;
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

    @GetMapping("/order")
    public ModelAndView pedido(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        // Recupera os dados do usuário
        Customer customer = userService.getLoggedUser(request);

        mv.setViewName("order");
        mv.addObject("orders", orderService.getOrdersByUserId(customer.getId()));
        return mv;
    }

    @GetMapping("/order/{id}/detail")
    public ModelAndView detalhe(@PathVariable("id") String id) {
        ModelAndView mv = new ModelAndView();

        Order order = orderService.getOrderById(id);

        mv.addObject("order", order);
        mv.addObject("details", order.getDetails());
        mv.addObject("orderId", id);
        mv.setViewName("detail");
        return mv;
    }

    @GetMapping("/order/{id}/cancel")
    public ModelAndView cancelar(@PathVariable("id") String id, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        // Recupera os dados do usuário
        Customer customer = userService.getLoggedUser(request);

        orderService.updateStatus(id, customer.getId(), false);

        mv.addObject("orderId", id);
        mv.setViewName("cancel");
        return mv;
    }
}
