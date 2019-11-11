package br.anhembi.funmodechild.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.anhembi.funmodechild.models.Usuario;

public interface RepositoryUsuario extends JpaRepository<Usuario, Long> {

	public Usuario findByEmail(String email);
}
