package br.anhembi.funmodechild.service;

import br.anhembi.funmodechild.entity.Customer;
import br.anhembi.funmodechild.model.common.PasswordNotMatchException;
import br.anhembi.funmodechild.model.request.UserRequest;
import br.anhembi.funmodechild.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(CustomerRepository customerRepository, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(UserRequest request) {
        Customer customer = request.toDoc(passwordEncoder.encode(request.password));
        customerRepository.save(customer);
    }

    public void updatePassword(String username, String oldPassword, String newPassword, String newPasswordConfirm) {
        Customer customer = customerRepository.findByEmail(username);

        // Checa se as novas senha são iguais
        if (!newPassword.equals(newPasswordConfirm)) {
            throw new PasswordNotMatchException("As senhas novas são diferentes");
        }

        // Checa se a senha antiga bate com a informada
        if (!passwordEncoder.matches(oldPassword, customer.getPassword())) {
            throw new PasswordNotMatchException("Senha antiga inválida");
        }

        // Altera a senha
        customer.setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer);
    }
}
