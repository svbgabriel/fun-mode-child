package br.anhembi.funmodechild.services;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.anhembi.funmodechild.models.Carrinho;
import br.anhembi.funmodechild.models.Pagamento;
import br.anhembi.funmodechild.models.Pedido;
import br.anhembi.funmodechild.models.PedidoDetalhe;
import br.anhembi.funmodechild.models.Produto;
import br.anhembi.funmodechild.models.Usuario;
import br.anhembi.funmodechild.repositories.RepositoryPagamento;
import br.anhembi.funmodechild.repositories.RepositoryPedido;
import br.anhembi.funmodechild.repositories.RepositoryPedidoDetalhe;
import br.anhembi.funmodechild.repositories.RepositoryProduto;

@Service
public class ServicePagamento {

	@Autowired
	RepositoryPagamento repositoryPagamento;

	@Autowired
	RepositoryPedido repositoryPedido;

	@Autowired
	RepositoryPedidoDetalhe repositoryPedidoDetalhe;

	@Autowired
	RepositoryProduto repositoryProduto;

	public Pagamento salvar(Carrinho carrinho, Usuario usuario, Pagamento pagamento) {
		double precoTotal = 0.0;
		Pedido pedido = new Pedido();

		AbstractMap<Long, Integer> listaProdutos = carrinho.getLista();
		Iterator<Long> keySetIterator = listaProdutos.keySet().iterator();
		List<PedidoDetalhe> detalhes = new ArrayList<>();
		while (keySetIterator.hasNext()) {
			Long sku = keySetIterator.next();
			Produto produto = repositoryProduto.getOne(sku);

			PedidoDetalhe detalhePedido = new PedidoDetalhe();
			detalhePedido.setPrecoItem(produto.getPreco());
			detalhePedido.setQuantidade(listaProdutos.get(sku));
			detalhePedido.setProduto(produto);

			detalhes.add(detalhePedido);

			precoTotal += detalhePedido.getPrecoItem() * detalhePedido.getQuantidade();
		}

		pedido.setPrecoTotal(precoTotal);
		pedido.setUsuario(usuario);
		Pedido pedidoSalvo = repositoryPedido.save(pedido);
		
		// Adiciona o pedido ao detalhe
		for (PedidoDetalhe detalhe : detalhes) {
			detalhe.setPedido(pedido);
		}
		repositoryPedidoDetalhe.saveAll(detalhes);

		if (pedidoSalvo.getId() > -1) {
			// Completa os dados de pagamento
			pagamento.setPedido(pedido);

			// Salva o pagamento
			repositoryPagamento.save(pagamento);			
		}

		return pagamento;
	}
}
