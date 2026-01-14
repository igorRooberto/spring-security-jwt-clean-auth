package com.example.EduTrack.identity.domain.model;

import com.example.EduTrack.identity.domain.exception.IdentityException;

public record Email(String address) {

    public Email(String address) {
        String cleanEmail = (address != null) ? address.trim() : null;

        if (cleanEmail == null || !cleanEmail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw IdentityException.conflict("Email inválido: " + address);
        }

        this.address = cleanEmail;
    }
}
