package br.anhembi.funmodechild.model.request;

public record AuthRequest(
    String username,
    String password
) {
}
