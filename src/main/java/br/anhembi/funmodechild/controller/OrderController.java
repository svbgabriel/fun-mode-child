package br.anhembi.funmodechild.controller;

import br.anhembi.funmodechild.model.common.LoggedUser;
import br.anhembi.funmodechild.model.request.OrderRequest;
import br.anhembi.funmodechild.model.response.OrderResponse;
import br.anhembi.funmodechild.service.OrderService;
import br.anhembi.funmodechild.util.AuthenticationUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/customer")
    public List<OrderResponse> getOrdersByCustomer() {
        LoggedUser customer = AuthenticationUtil.getLoggedUser();

        return orderService.findOrdersByUserId(customer.getId());
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable("orderId") String id) {
        LoggedUser loggedUser = AuthenticationUtil.getLoggedUser();

        return orderService.findOrderById(id, loggedUser.getId());
    }

    @PutMapping("/{orderId}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrderById(@PathVariable("orderId") String id) {
        LoggedUser loggedUser = AuthenticationUtil.getLoggedUser();

        orderService.updateStatus(id, false, loggedUser.getId());
    }

    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        LoggedUser loggedUser = AuthenticationUtil.getLoggedUser();

        return orderService.createOrder(request, loggedUser.getId());
    }
}
