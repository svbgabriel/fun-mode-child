package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Usuario;
import br.anhembi.funmodechild.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void salvar(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        userRepository.save(usuario);
    }

    public void updatePassword(Principal user, String oldPassword, String newPassword) {
        String username = user.getName();
        Usuario usuario = userRepository.findByEmail(username);
        // Checa se a senha antiga bate com a informada
        if (passwordEncoder.matches(oldPassword, usuario.getSenha())) {
            // Altera a senha
            usuario.setSenha(passwordEncoder.encode(newPassword));
            userRepository.save(usuario);
        } else {
            // TODO: Retornar um erro
        }
    }

    public Usuario getLoggedUser(HttpServletRequest request) {
        Principal user = request.getUserPrincipal();
        return getUserByEmail(user.getName());
    }
}
