package br.anhembi.funmodechild.model.request

@JvmRecord
data class AuthRequest(
    @JvmField val username: String,
    @JvmField val password: String
)
