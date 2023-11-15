package br.anhembi.funmodechild.model.response;

import br.anhembi.funmodechild.entity.Product;

public record CartInfoRelation(Product product, int quantity) {

    public double calculateTotalPrice() {
        return this.product.getPrice() * quantity;
    }

    public String formatTotalPrice() {
        return String.format("%1$,.2f", this.calculateTotalPrice());
    }
}
