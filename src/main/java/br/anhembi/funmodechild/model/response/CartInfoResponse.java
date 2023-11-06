package br.anhembi.funmodechild.model.response;

import java.util.List;

public record CartInfoResponse(List<CartInfoRelation> products, double totalCartPrice) {

    public String formatTotalPrice() {
        return String.format("%1$,.2f", this.totalCartPrice());
    }
}
