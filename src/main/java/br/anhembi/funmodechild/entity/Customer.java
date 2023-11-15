package br.anhembi.funmodechild.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document("customers")
public class Customer {

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
    private boolean active;

    @Data
    public static class Address {
        private String addressLine;
        private String number;
        private String addressComplement;
        private String neighbourhood;
        private String postalCode;
        private String city;
        private String state;
    }
}
