package br.anhembi.funmodechild.repository;

import br.anhembi.funmodechild.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Categoria, Long> {

}
