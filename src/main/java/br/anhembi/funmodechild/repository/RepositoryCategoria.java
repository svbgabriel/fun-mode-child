package br.anhembi.funmodechild.repository;

import br.anhembi.funmodechild.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryCategoria extends JpaRepository<Categoria, Long> {

}
