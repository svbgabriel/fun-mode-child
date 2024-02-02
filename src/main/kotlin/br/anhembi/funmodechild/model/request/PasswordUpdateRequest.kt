package br.anhembi.funmodechild.model.request

data class PasswordUpdateRequest(
    val oldPassword: String,
    val newPassword: String,
    val newPasswordConfirm: String
)
