package br.anhembi.funmodechild.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.anhembi.funmodechild.models.Usuario;
import br.anhembi.funmodechild.repositories.RepositoryUsuario;

@Service
public class ServiceUsuario {

	@Autowired
	private RepositoryUsuario repositoryUsuario;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public Usuario encontrarPorEmail(String email) {
		return repositoryUsuario.findByEmail(email);
	}

	public void salvar(Usuario usuario) {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		repositoryUsuario.save(usuario);
	}
}
