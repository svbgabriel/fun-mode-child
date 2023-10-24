package br.anhembi.funmodechild.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.anhembi.funmodechild.models.Categoria;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryCategoria extends JpaRepository<Categoria, Long> {

}
