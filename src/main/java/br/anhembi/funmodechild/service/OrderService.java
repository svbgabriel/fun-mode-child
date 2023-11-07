package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Pedido;
import br.anhembi.funmodechild.entity.PedidoDetalhe;
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

    public Pedido getOrderById(long id) {
        return orderRepository.getReferenceById(id);
    }

    public void updateStatus(long id, long userId, boolean status) {
        try {
            Pedido pedido = orderRepository.findByUsuarioAndId(userId, id);
            pedido.setAtivo(status);
            orderRepository.save(pedido);
        } catch (Exception e) {
            // TODO Tratar um erro
        }
    }

    public List<Pedido> getOrdersByUserId(long id) {
        return orderRepository.findByUsuario(id);
    }

    public List<PedidoDetalhe> getOrderDetailsByOrderId(long id, long userId) {
        return orderDetailRepository.findByPedido(id, userId);
    }
}
