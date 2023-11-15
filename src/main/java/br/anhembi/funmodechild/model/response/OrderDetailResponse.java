package br.anhembi.funmodechild.model.response;

public record OrderDetailResponse(ProductResponse product,
                                  double price,
                                  int quantity) {
}
