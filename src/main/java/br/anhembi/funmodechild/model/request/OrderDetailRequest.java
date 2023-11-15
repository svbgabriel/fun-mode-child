package br.anhembi.funmodechild.model.request;

public record OrderDetailRequest(
    String productId,
    int quantity
) {
}
