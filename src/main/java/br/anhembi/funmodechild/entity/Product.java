package br.anhembi.funmodechild.entity;

import br.anhembi.funmodechild.model.response.ProductResponse;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@Document("products")
public class Product {

    @Id
    private String id;
    private int sku;
    private String name;
    private String description;
    private double price;
    private boolean promoted;
    @DocumentReference(lazy = true)
    private Category category;
    private String reference;
    private String referenceBig;
    private LocalDateTime createdAt = LocalDateTime.now();
    private int quantity;

    public ProductResponse toApiResponse() {
        return new ProductResponse(
            this.id,
            this.sku,
            this.name,
            this.description,
            this.price,
            this.promoted,
            this.category.toApiResponse(),
            this.createdAt,
            this.quantity
        );
    }
}
