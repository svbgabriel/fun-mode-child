package br.anhembi.funmodechild.model.response;

import java.time.LocalDateTime;

public record ProductResponse(String id,
                              int sku,
                              String name,
                              String description,
                              double price,
                              boolean promoted,
                              CategoryResponse category,
                              LocalDateTime createdAt,
                              int quantity
) {
}
