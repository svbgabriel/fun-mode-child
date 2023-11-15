package br.anhembi.funmodechild.entity;

import br.anhembi.funmodechild.model.response.CategoryResponse;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("categories")
public class Category {

    @Id
    private String id;
    private String name;

    public CategoryResponse toApiResponse() {
        return new CategoryResponse(this.id, this.name);
    }
}
