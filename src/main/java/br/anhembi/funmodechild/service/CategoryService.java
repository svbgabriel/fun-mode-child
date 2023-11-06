package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Categoria;
import br.anhembi.funmodechild.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Categoria> getAll() {
        return categoryRepository.findAll();
    }
}
