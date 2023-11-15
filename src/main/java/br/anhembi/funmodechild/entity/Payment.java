package br.anhembi.funmodechild.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@Document("payments")
public class Payment {

    @Id
    private long id;
    @DocumentReference(lazy = true)
    private Order order;
    private String cardNumber;
    private String cardName;
    private int month;
    private int year;
    private int cvv;
    private int statements;
    private LocalDateTime createdAt = LocalDateTime.now();
}
