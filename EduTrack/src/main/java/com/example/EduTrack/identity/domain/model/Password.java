package com.example.EduTrack.identity.domain.model;

import com.example.EduTrack.identity.domain.exception.IdentityException;

public record Password(String value) {

    public Password(String value) {
        String cleanPassword = (value != null) ? value.trim() : null;

        if (cleanPassword == null || cleanPassword.isBlank()) {
            throw IdentityException.validate("Senha obrigatória.");
        }
        if (cleanPassword.length() < 8) {
            throw IdentityException.validate("A senha deve ter no mínimo 8 caracteres.");
        }
        this.value = cleanPassword;
    }
}

