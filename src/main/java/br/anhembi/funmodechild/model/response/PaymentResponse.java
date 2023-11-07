package br.anhembi.funmodechild.model.response;

import br.anhembi.funmodechild.entity.Payment;

import java.util.List;

public record PaymentResponse(Payment paymentData, List<String> errors) {
}
