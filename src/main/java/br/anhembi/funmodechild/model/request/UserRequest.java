package br.anhembi.funmodechild.model.request;

import br.anhembi.funmodechild.model.common.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;

public record UserRequest(
    String name,
    String surname,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate,
    @Email
    String email,
    Address address,
    String customerIdentification,
    String phoneNumber,
    String password
) {
}
