package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.model.Carrinho;
import br.anhembi.funmodechild.model.Pagamento;
import br.anhembi.funmodechild.model.Pedido;
import br.anhembi.funmodechild.model.PedidoDetalhe;
import br.anhembi.funmodechild.model.Produto;
import br.anhembi.funmodechild.model.Usuario;
import br.anhembi.funmodechild.repository.RepositoryPagamento;
import br.anhembi.funmodechild.repository.RepositoryPedido;
import br.anhembi.funmodechild.repository.RepositoryPedidoDetalhe;
import br.anhembi.funmodechild.repository.RepositoryProduto;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ServicePagamento {

    private final RepositoryPagamento repositoryPagamento;
    private final RepositoryPedido repositoryPedido;
    private final RepositoryPedidoDetalhe repositoryPedidoDetalhe;
    private final RepositoryProduto repositoryProduto;

    public ServicePagamento(RepositoryPagamento repositoryPagamento,
                            RepositoryPedido repositoryPedido,
                            RepositoryPedidoDetalhe repositoryPedidoDetalhe,
                            RepositoryProduto repositoryProduto
    ) {
        this.repositoryPagamento = repositoryPagamento;
        this.repositoryPedido = repositoryPedido;
        this.repositoryPedidoDetalhe = repositoryPedidoDetalhe;
        this.repositoryProduto = repositoryProduto;
    }

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