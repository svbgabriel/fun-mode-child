package br.anhembi.funmodechild.config;

import br.anhembi.funmodechild.entity.Customer;
import br.anhembi.funmodechild.repository.CustomerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DBUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public DBUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer user = customerRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + email);
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new User(
            user.getEmail(), user.getPassword(), enabled, accountNonExpired,
            credentialsNonExpired, accountNonLocked, getAuthorities(new ArrayList<>()));
    }

    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
