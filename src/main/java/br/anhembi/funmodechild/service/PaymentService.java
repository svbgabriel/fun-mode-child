package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Payment;
import br.anhembi.funmodechild.model.common.Cart;
import br.anhembi.funmodechild.entity.Order;
import br.anhembi.funmodechild.entity.OrderDetail;
import br.anhembi.funmodechild.entity.Product;
import br.anhembi.funmodechild.entity.Customer;
import br.anhembi.funmodechild.model.response.PaymentResponse;
import br.anhembi.funmodechild.repository.PaymentRepository;
import br.anhembi.funmodechild.repository.OrderRepository;
import br.anhembi.funmodechild.repository.OrderDetailRepository;
import br.anhembi.funmodechild.repository.ProductRepository;
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
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          OrderRepository orderRepository,
                          OrderDetailRepository orderDetailRepository,
                          ProductRepository productRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
    }

    private Payment save(Cart cart, Customer customer, Payment payment) {
        double precoTotal = 0.0;
        Order order = new Order();

        AbstractMap<Long, Integer> listaProdutos = cart.getLista();
        Iterator<Long> keySetIterator = listaProdutos.keySet().iterator();
        List<OrderDetail> detalhes = new ArrayList<>();
        while (keySetIterator.hasNext()) {
            Long sku = keySetIterator.next();
            Product product = productRepository.findBySku(sku).orElseThrow();

            OrderDetail detalhePedido = new OrderDetail();
            detalhePedido.setPrecoItem(product.getPreco());
            detalhePedido.setQuantidade(listaProdutos.get(sku));
            detalhePedido.setProduct(product);

            detalhes.add(detalhePedido);

            precoTotal += detalhePedido.getPrecoItem() * detalhePedido.getQuantidade();
        }

        order.setPrecoTotal(precoTotal);
        order.setCustomer(customer);
        Order savedOrder = orderRepository.save(order);

        // Adiciona o pedido ao detalhe
        for (OrderDetail detalhe : detalhes) {
            detalhe.setOrder(order);
        }
        orderDetailRepository.saveAll(detalhes);

        if (savedOrder.getId() > -1) {
            // Completa os dados de pagamento
            payment.setOrder(order);

            // Salva o pagamento
            paymentRepository.save(payment);
        }

        return payment;
    }

    public PaymentResponse makePayment(HttpSession session,
                                       HttpServletRequest request,
                                       Customer customer,
                                       Cart cart) {
        List<String> erroSalvar;
        PaymentResponse paymentResponse;
        if (request.getParameter("save") != null && cart != null) {
            // Clicou em Salvar Pedido
            erroSalvar = validatePaymentRequest(request);

            Payment paymentResposta;
            if (erroSalvar.isEmpty() && !cart.getLista().isEmpty()) {
                // Salva os dados de pagamento
                Payment payment = paymentMapper(request);

                paymentResposta = save(cart, customer, payment);
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

        if (request.getParameter("card-number") == null) {
            errorMessages.add("Informe o número do cartão");
        }
        if (request.getParameter("card-name") == null) {
            errorMessages.add("Informe o nome que está no cartão");
        }
        if (request.getParameter("month") == null) {
            errorMessages.add("Informe o mês de validade do cartão");
        }
        if (request.getParameter("year") == null) {
            errorMessages.add("Informe o ano de validade do cartão");
        }
        if (request.getParameter("cvv") == null) {
            errorMessages.add("Informe o código de verificação do cartão");
        }
        if (request.getParameter("statements") == null) {
            errorMessages.add("Informe o número de parcelas");
        }

        return errorMessages;
    }

    private Payment paymentMapper(HttpServletRequest request) {
        Payment payment = new Payment();
        payment.setNumeroCartao(request.getParameter("card-number"));
        payment.setNomeCartao(request.getParameter("card-name"));
        payment.setValidadeMes(Integer.parseInt(request.getParameter("month")));
        payment.setValidadeAno(Integer.parseInt(request.getParameter("year")));
        payment.setCodigo(Integer.parseInt(request.getParameter("cvv")));
        payment.setParcelas(Integer.parseInt(request.getParameter("statements")));
        payment.setDataPagamento(Date.from(Instant.now()));
        return payment;
    }
}
