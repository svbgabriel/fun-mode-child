package br.anhembi.funmodechild.model.response;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(String id,
                            LocalDateTime createdAt,
                            List<OrderDetailResponse> details,
                            double totalPrice,
                            boolean active) {
}
