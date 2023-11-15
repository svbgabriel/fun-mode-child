package br.anhembi.funmodechild.entity;

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

    /**
     * Retorna o preço formatado como 0.000,00.
     *
     * @return o preço formatado com duas casas decimais.
     */
    public String formatPrice() {
        return String.format("%1$,.2f", this.price);
    }
}
