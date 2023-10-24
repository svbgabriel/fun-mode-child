package br.anhembi.funmodechild.repository;

import br.anhembi.funmodechild.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryUsuario extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);
}
