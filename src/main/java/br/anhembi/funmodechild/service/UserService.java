package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Customer;
import br.anhembi.funmodechild.model.common.PasswordNotMatchException;
import br.anhembi.funmodechild.repository.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(CustomerRepository customerRepository, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer getUserByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public void create(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
    }

    public void updatePassword(Principal user, String oldPassword, String newPassword, String newPasswordConfirm) {
        // Checa se as novas senha são iguais
        if (newPassword.equals(newPasswordConfirm)) {
            throw new PasswordNotMatchException("As senhas novas são diferentes");
        }

        String username = user.getName();
        Customer customer = customerRepository.findByEmail(username);

        // Checa se a senha antiga bate com a informada
        if (!passwordEncoder.matches(oldPassword, customer.getPassword())) {
            throw new PasswordNotMatchException("Senha antiga inválida");
        }

        // Altera a senha
        customer.setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer);
    }

    public Customer getLoggedUser(HttpServletRequest request) {
        Principal user = request.getUserPrincipal();
        return getUserByEmail(user.getName());
    }
}
