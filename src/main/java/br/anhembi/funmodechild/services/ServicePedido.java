package br.anhembi.funmodechild.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.anhembi.funmodechild.models.Pedido;
import br.anhembi.funmodechild.repositories.RepositoryPedido;

@Service
public class ServicePedido {

	@Autowired
	RepositoryPedido repositoryPedido;

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
