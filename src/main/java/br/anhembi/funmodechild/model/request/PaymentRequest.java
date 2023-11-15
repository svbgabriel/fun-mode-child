package br.anhembi.funmodechild.model.request;

public record PaymentRequest(
    String cardNumber,
    String cardName,
    Integer month,
    Integer year,
    Integer cvv,
    Integer statements
) {
}
