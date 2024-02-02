package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Payment;
import br.anhembi.funmodechild.entity.Order;
import br.anhembi.funmodechild.model.common.PaymentException;
import br.anhembi.funmodechild.model.request.PaymentRequest;
import br.anhembi.funmodechild.model.response.PaymentResponse;
import br.anhembi.funmodechild.repository.PaymentRepository;
import br.anhembi.funmodechild.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    private Payment save(Payment payment, String orderId, String customerId) {
        Order order = orderRepository.findByIdAndCustomerId(orderId, customerId);

        if (order == null) {
            throw new PaymentException("Order doesn't match the customer");
        }

        // Completa os dados de pagamento
        payment.setOrder(order);

        // Salva o pagamento
        paymentRepository.save(payment);

        return payment;
    }

    public PaymentResponse makePayment(PaymentRequest request, String orderId, String customerId) {
        // Salva os dados de pagamento
        Payment payment = request.toDoc();

        Payment paymentResposta = save(payment, orderId, customerId);

        return new PaymentResponse(paymentResposta);
    }
}
