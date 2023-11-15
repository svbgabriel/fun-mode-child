package br.anhembi.funmodechild.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("categories")
public class Category {

    @Id
    private String id;
    private String name;
}
