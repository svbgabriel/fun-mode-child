package br.anhembi.funmodechild.model.request;

import java.util.List;

public record OrderRequest(
    List<OrderDetailRequest> details
) {
}
