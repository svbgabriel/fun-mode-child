package br.anhembi.funmodechild.model.common

data class Address(
    val addressLine: String? = null,
    val number: String? = null,
    val addressComplement: String? = null,
    val neighbourhood: String? = null,
    val postalCode: String? = null,
    val city: String? = null,
    val state: String? = null
)
