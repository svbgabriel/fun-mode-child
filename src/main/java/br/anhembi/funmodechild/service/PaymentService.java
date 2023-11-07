package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Payment;
import br.anhembi.funmodechild.model.Carrinho;
import br.anhembi.funmodechild.entity.Pedido;
import br.anhembi.funmodechild.entity.PedidoDetalhe;
import br.anhembi.funmodechild.entity.Product;
import br.anhembi.funmodechild.entity.Usuario;
import br.anhembi.funmodechild.model.response.PaymentResponse;
import br.anhembi.funmodechild.repository.PaymentRepository;
import br.anhembi.funmodechild.repository.RepositoryPedido;
import br.anhembi.funmodechild.repository.RepositoryPedidoDetalhe;
import br.anhembi.funmodechild.repository.RepositoryProduto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static br.anhembi.funmodechild.common.Constants.SHOPPING_CART;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RepositoryPedido repositoryPedido;
    private final RepositoryPedidoDetalhe repositoryPedidoDetalhe;
    private final RepositoryProduto repositoryProduto;

    public PaymentService(PaymentRepository paymentRepository,
                          RepositoryPedido repositoryPedido,
                          RepositoryPedidoDetalhe repositoryPedidoDetalhe,
                          RepositoryProduto repositoryProduto
    ) {
        this.paymentRepository = paymentRepository;
        this.repositoryPedido = repositoryPedido;
        this.repositoryPedidoDetalhe = repositoryPedidoDetalhe;
        this.repositoryProduto = repositoryProduto;
    }

    private Payment save(Carrinho carrinho, Usuario usuario, Payment payment) {
        double precoTotal = 0.0;
        Pedido pedido = new Pedido();

        AbstractMap<Long, Integer> listaProdutos = carrinho.getLista();
        Iterator<Long> keySetIterator = listaProdutos.keySet().iterator();
        List<PedidoDetalhe> detalhes = new ArrayList<>();
        while (keySetIterator.hasNext()) {
            Long sku = keySetIterator.next();
            Product product = repositoryProduto.findBySku(sku).orElseThrow();

            PedidoDetalhe detalhePedido = new PedidoDetalhe();
            detalhePedido.setPrecoItem(product.getPreco());
            detalhePedido.setQuantidade(listaProdutos.get(sku));
            detalhePedido.setProduct(product);

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
            payment.setPedido(pedido);

            // Salva o pagamento
            paymentRepository.save(payment);
        }

        return payment;
    }

    public PaymentResponse makePayment(HttpSession session,
                                       HttpServletRequest request,
                                       Usuario usuario,
                                       Carrinho carrinho) {
        List<String> erroSalvar;
        PaymentResponse paymentResponse;
        if (request.getParameter("salvar") != null && carrinho != null) {
            // Clicou em Salvar Pedido
            erroSalvar = validatePaymentRequest(request);

            Payment paymentResposta;
            if (erroSalvar.isEmpty() && !carrinho.getLista().isEmpty()) {
                // Salva os dados de pagamento
                Payment payment = paymentMapper(request);

                paymentResposta = save(carrinho, usuario, payment);
                // Esvazia o carrinho.
                session.setAttribute(SHOPPING_CART, null);
            } else {
                paymentResposta = null;
            }

            paymentResponse = new PaymentResponse(paymentResposta, erroSalvar);
        } else {
            paymentResponse = new PaymentResponse(null, new ArrayList<>());
        }

        return paymentResponse;
    }

    private List<String> validatePaymentRequest(HttpServletRequest request) {
        List<String> errorMessages = new ArrayList<>();

        if (request.getParameter("numerocartao") == null) {
            errorMessages.add("Informe o número do cartão");
        }
        if (request.getParameter("nomecartao") == null) {
            errorMessages.add("Informe o nome que está no cartão");
        }
        if (request.getParameter("validademes") == null) {
            errorMessages.add("Informe o mês de validade do cartão");
        }
        if (request.getParameter("validadeano") == null) {
            errorMessages.add("Informe o ano de validade do cartão");
        }
        if (request.getParameter("codigo") == null) {
            errorMessages.add("Informe o código de verificação do cartão");
        }
        if (request.getParameter("parcelas") == null) {
            errorMessages.add("Informe o número de parcelas");
        }

        return errorMessages;
    }

    private Payment paymentMapper(HttpServletRequest request) {
        Payment payment = new Payment();
        payment.setNumeroCartao(request.getParameter("numerocartao"));
        payment.setNomeCartao(request.getParameter("nomecartao"));
        payment.setValidadeMes(Integer.parseInt(request.getParameter("validademes")));
        payment.setValidadeAno(Integer.parseInt(request.getParameter("validadeano")));
        payment.setCodigo(Integer.parseInt(request.getParameter("codigo")));
        payment.setParcelas(Integer.parseInt(request.getParameter("parcelas")));
        payment.setDataPagamento(Date.from(Instant.now()));
        return payment;
    }
}
