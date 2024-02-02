package br.anhembi.funmodechild.model.request

@JvmRecord
data class PasswordUpdateRequest(
    @JvmField val oldPassword: String,
    @JvmField val newPassword: String,
    @JvmField val newPasswordConfirm: String
)
