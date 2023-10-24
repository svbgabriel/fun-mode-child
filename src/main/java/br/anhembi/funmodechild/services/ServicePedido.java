package br.anhembi.funmodechild.services;

import br.anhembi.funmodechild.models.Pedido;
import br.anhembi.funmodechild.repositories.RepositoryPedido;
import org.springframework.stereotype.Service;

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
