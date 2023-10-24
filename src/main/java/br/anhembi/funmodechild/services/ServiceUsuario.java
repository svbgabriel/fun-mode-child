package br.anhembi.funmodechild.services;

import br.anhembi.funmodechild.models.Usuario;
import br.anhembi.funmodechild.repositories.RepositoryUsuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ServiceUsuario {

    private final RepositoryUsuario repositoryUsuario;
    private final BCryptPasswordEncoder passwordEncoder;

    public ServiceUsuario(RepositoryUsuario repositoryUsuario, BCryptPasswordEncoder passwordEncoder) {
        this.repositoryUsuario = repositoryUsuario;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario encontrarPorEmail(String email) {
        return repositoryUsuario.findByEmail(email);
    }

    public void salvar(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        repositoryUsuario.save(usuario);
    }

    public void atualizar(Principal user, String oldPassword, String newPassword) {
        String username = user.getName();
        Usuario usuario = repositoryUsuario.findByEmail(username);
        // Checa se a senha antiga bate com a informada
        if (passwordEncoder.matches(oldPassword, usuario.getSenha())) {
            // Altera a senha
            usuario.setSenha(passwordEncoder.encode(newPassword));
            repositoryUsuario.save(usuario);
        } else {
            // TODO: Retornar um erro
        }
    }
}
