package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Order;
import br.anhembi.funmodechild.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public void updateStatus(String id, String userId, boolean status) {
        try {
            Order order = orderRepository.findByIdAndCustomerId(id, userId);
            order.setActive(status);
            orderRepository.save(order);
        } catch (Exception e) {
            // TODO Tratar um erro
        }
    }

    public List<Order> getOrdersByUserId(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}
