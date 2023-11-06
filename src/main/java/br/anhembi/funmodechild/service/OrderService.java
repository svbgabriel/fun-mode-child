package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Pedido;
import br.anhembi.funmodechild.entity.PedidoDetalhe;
import br.anhembi.funmodechild.repository.RepositoryPedido;
import br.anhembi.funmodechild.repository.RepositoryPedidoDetalhe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final RepositoryPedido repositoryPedido;
    private final RepositoryPedidoDetalhe repositoryPedidoDetalhe;

    public OrderService(RepositoryPedido repositoryPedido, RepositoryPedidoDetalhe repositoryPedidoDetalhe) {
        this.repositoryPedido = repositoryPedido;
        this.repositoryPedidoDetalhe = repositoryPedidoDetalhe;
    }

    public Pedido getOrderById(long id) {
        return repositoryPedido.getReferenceById(id);
    }

    public void updateStatus(long id, long userId, boolean status) {
        try {
            Pedido pedido = repositoryPedido.findByUsuarioAndId(userId, id);
            pedido.setAtivo(status);
            repositoryPedido.save(pedido);
        } catch (Exception e) {
            // TODO Tratar um erro
        }
    }

    public List<Pedido> getOrdersByUserId(long id) {
        return repositoryPedido.findByUsuario(id);
    }

    public List<PedidoDetalhe> getOrderDetailsByOrderId(long id, long userId) {
        return repositoryPedidoDetalhe.findByPedido(id, userId);
    }
}
