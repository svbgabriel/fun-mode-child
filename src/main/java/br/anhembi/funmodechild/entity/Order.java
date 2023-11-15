package br.anhembi.funmodechild.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("orders")
public class Order {

    @Id
    private String id;
    private LocalDateTime createdAt = LocalDateTime.now();
    @DocumentReference(lazy = true)
    private Customer customer;
    private List<OrderDetail> details = new ArrayList<>();
    private double totalPrice;
    private boolean active = true;

    @Data
    public static class OrderDetail {
        @DocumentReference(lazy = true)
        private Product product;
        private double price;
        private int quantity;
    }

    public String formatTotalPrice() {
        return String.format("%1$,.2f", this.totalPrice);
    }
}
