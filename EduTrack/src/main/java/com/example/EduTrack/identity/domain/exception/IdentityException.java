package com.example.EduTrack.identity.domain.exception;

import com.example.EduTrack.shared.domain.exception.DomainException;

public class IdentityException extends DomainException {

    public IdentityException(String message, int status) {
        super(message, status);
    }

    public static IdentityException validate(String message) {
        return new IdentityException(message, 422);
    }

    public static IdentityException conflict(String message) {
        return new IdentityException(message, 409);
    }
}
