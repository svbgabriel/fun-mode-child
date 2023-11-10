package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Customer;
import br.anhembi.funmodechild.model.common.PasswordNotMatchException;
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

    public Customer getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void salvar(Customer customer) {
        customer.setSenha(passwordEncoder.encode(customer.getSenha()));
        userRepository.save(customer);
    }

    public void updatePassword(Principal user, String oldPassword, String newPassword, String newPasswordConfirm) {
        // Checa se as novas senha são iguais
        if (newPassword.equals(newPasswordConfirm)) {
            throw new PasswordNotMatchException("As senhas novas são diferentes");
        }

        String username = user.getName();
        Customer customer = userRepository.findByEmail(username);

        // Checa se a senha antiga bate com a informada
        if (!passwordEncoder.matches(oldPassword, customer.getSenha())) {
            throw new PasswordNotMatchException("Senha antiga inválida");
        }

        // Altera a senha
        customer.setSenha(passwordEncoder.encode(newPassword));
        userRepository.save(customer);
    }

    public Customer getLoggedUser(HttpServletRequest request) {
        Principal user = request.getUserPrincipal();
        return getUserByEmail(user.getName());
    }
}
