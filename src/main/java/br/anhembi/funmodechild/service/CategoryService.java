package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Category;
import br.anhembi.funmodechild.model.response.CategoryResponse;
import br.anhembi.funmodechild.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll()
            .stream().map(Category::toApiResponse)
            .toList();
    }
}
