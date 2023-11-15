package br.anhembi.funmodechild.model.common;

import lombok.Data;

@Data
public class Address {
    private String addressLine;
    private String number;
    private String addressComplement;
    private String neighbourhood;
    private String postalCode;
    private String city;
    private String state;
}
