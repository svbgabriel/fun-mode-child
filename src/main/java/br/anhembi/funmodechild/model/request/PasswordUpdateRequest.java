package br.anhembi.funmodechild.model.request;

public record PasswordUpdateRequest(
    String oldPassword,
    String newPassword,
    String newPasswordConfirm
) {
}
