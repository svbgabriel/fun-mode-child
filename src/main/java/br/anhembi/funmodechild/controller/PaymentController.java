package br.anhembi.funmodechild.controller;

import br.anhembi.funmodechild.entity.Customer;
import br.anhembi.funmodechild.model.request.PaymentRequest;
import br.anhembi.funmodechild.model.response.PaymentResponse;
import br.anhembi.funmodechild.service.PaymentService;
import br.anhembi.funmodechild.util.AuthenticationUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(value = "/orders/{orderId}/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    @Tag(name = "Orders")
    public PaymentResponse doPayment(@RequestBody PaymentRequest request, @PathVariable String orderId) {
        // Recupera os dados do usuário
        Customer customer = AuthenticationUtil.getCustomer();

        // Faz o pagamento
        return paymentService.makePayment(request, orderId, customer.getId());
    }
}
