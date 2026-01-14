package com.example.EduTrack.shared.domain.exception;

public class DomainException extends RuntimeException {

    private final int status;

    protected DomainException(String message, int status){
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static DomainException validationError(String message){
        throw new DomainException(message, 422);
    }
}
