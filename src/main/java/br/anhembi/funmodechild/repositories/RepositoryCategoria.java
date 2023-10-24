package br.anhembi.funmodechild.repositories;

import br.anhembi.funmodechild.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryCategoria extends JpaRepository<Categoria, Long> {

}
