package br.anhembi.funmodechild.entity;

import br.anhembi.funmodechild.model.response.OrderDetailResponse;
import br.anhembi.funmodechild.model.response.OrderResponse;
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

    public OrderResponse toApiResponse() {
        return new OrderResponse(
            this.id,
            this.createdAt,
            this.details.stream().map(OrderDetail::toApiResponse).toList(),
            this.totalPrice,
            this.active
        );
    }

    @Data
    public static class OrderDetail {
        @DocumentReference(lazy = true)
        private Product product;
        private double price;
        private int quantity;

        public OrderDetailResponse toApiResponse() {
            return new OrderDetailResponse(this.product.toApiResponse(), this.price, this.quantity);
        }
    }
}
