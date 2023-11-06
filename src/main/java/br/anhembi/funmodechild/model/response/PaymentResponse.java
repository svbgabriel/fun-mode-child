package br.anhembi.funmodechild.model.response;

import br.anhembi.funmodechild.entity.Pagamento;

import java.util.List;

public record PaymentResponse(Pagamento paymentData, List<String> errors) {
}
