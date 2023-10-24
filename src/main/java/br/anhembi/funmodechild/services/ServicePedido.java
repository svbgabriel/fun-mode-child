package br.anhembi.funmodechild.services;

import org.springframework.stereotype.Service;

import br.anhembi.funmodechild.models.Pedido;
import br.anhembi.funmodechild.repositories.RepositoryPedido;

@Service
public class ServicePedido {

    private final RepositoryPedido repositoryPedido;

    public ServicePedido(RepositoryPedido repositoryPedido) {
        this.repositoryPedido = repositoryPedido;
    }

    public boolean atualizarStatus(Pedido pedido, boolean status) {
        try {
            pedido.setAtivo(status);
            repositoryPedido.save(pedido);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
