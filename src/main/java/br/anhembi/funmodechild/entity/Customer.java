package br.anhembi.funmodechild.entity;

import br.anhembi.funmodechild.model.common.Address;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Document("customers")
public class Customer implements UserDetails {

    @Id
    private String id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String email;
    private Address address;
    private String customerIdentification;
    private String phoneNumber;
    private String password;
    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;
    private boolean accountNonLocked = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
