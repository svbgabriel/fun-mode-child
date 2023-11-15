package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Payment;
import br.anhembi.funmodechild.entity.Order;
import br.anhembi.funmodechild.model.common.PaymentException;
import br.anhembi.funmodechild.model.request.PaymentRequest;
import br.anhembi.funmodechild.model.response.PaymentResponse;
import br.anhembi.funmodechild.repository.PaymentRepository;
import br.anhembi.funmodechild.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    private Payment save(Payment payment, String orderId, String customerId) {
        Order order = orderRepository.findByIdAndCustomerId(orderId, customerId)
            .orElseThrow(() -> new PaymentException("Order doesn't match the customer"));

        // Completa os dados de pagamento
        payment.setOrder(order);

        // Salva o pagamento
        paymentRepository.save(payment);

        return payment;
    }

    public PaymentResponse makePayment(PaymentRequest request, String orderId, String customerId) {
        // Clicou em Salvar Pedido
        List<String> errors = validatePaymentRequest(request);

        PaymentResponse paymentResponse;
        if (errors.isEmpty()) {
            // Salva os dados de pagamento
            Payment payment = paymentMapper(request);

            Payment paymentResposta = save(payment, orderId, customerId);

            paymentResponse = new PaymentResponse(paymentResposta, errors);
        } else {
            paymentResponse = new PaymentResponse(null, errors);
        }

        return paymentResponse;
    }

    private List<String> validatePaymentRequest(PaymentRequest request) {
        List<String> errorMessages = new ArrayList<>();

        if (request.cardNumber() == null) {
            errorMessages.add("Informe o número do cartão");
        }
        if (request.cardName() == null) {
            errorMessages.add("Informe o nome que está no cartão");
        }
        if (request.month() == null) {
            errorMessages.add("Informe o mês de validade do cartão");
        }
        if (request.year() == null) {
            errorMessages.add("Informe o ano de validade do cartão");
        }
        if (request.cvv() == null) {
            errorMessages.add("Informe o código de verificação do cartão");
        }
        if (request.statements() == null) {
            errorMessages.add("Informe o número de parcelas");
        }

        return errorMessages;
    }

    private Payment paymentMapper(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setCardNumber(request.cardNumber());
        payment.setCardName(request.cardName());
        payment.setMonth(request.month());
        payment.setYear(request.year());
        payment.setCvv(request.cvv());
        payment.setStatements(request.statements());
        payment.setCreatedAt(LocalDateTime.now());
        return payment;
    }
}
