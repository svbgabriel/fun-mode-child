package br.anhembi.funmodechild.controller

import br.anhembi.funmodechild.model.request.PaymentRequest
import br.anhembi.funmodechild.model.response.PaymentResponse
import br.anhembi.funmodechild.service.PaymentService
import br.anhembi.funmodechild.util.AuthenticationUtil.getLoggedUser
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentController(private val paymentService: PaymentService) {

    @PostMapping(value = ["/orders/{orderId}/payment"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Tag(name = "Orders")
    fun doPayment(@RequestBody request: PaymentRequest, @PathVariable orderId: String): PaymentResponse {
        // Recupera os dados do usuário
        val loggedUser = getLoggedUser()

        // Faz o pagamento
        return paymentService.makePayment(request, orderId, loggedUser.id)
    }
}
