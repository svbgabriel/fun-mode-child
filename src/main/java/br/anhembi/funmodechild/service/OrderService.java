package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Order;
import br.anhembi.funmodechild.entity.OrderDetail;
import br.anhembi.funmodechild.repository.OrderRepository;
import br.anhembi.funmodechild.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Order getOrderById(long id) {
        return orderRepository.getReferenceById(id);
    }

    public void updateStatus(long id, long userId, boolean status) {
        try {
            Order order = orderRepository.findByUsuarioAndId(userId, id);
            order.setAtivo(status);
            orderRepository.save(order);
        } catch (Exception e) {
            // TODO Tratar um erro
        }
    }

    public List<Order> getOrdersByUserId(long id) {
        return orderRepository.findByUsuario(id);
    }

    public List<OrderDetail> getOrderDetailsByOrderId(long id, long userId) {
        return orderDetailRepository.findByPedido(id, userId);
    }
}
