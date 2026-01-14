package com.example.EduTrack.shared.domain.validation;

import com.example.EduTrack.shared.domain.exception.DomainException;

public class Assertion {

    //Construtor privado para impedir que alguém faça "new Assertion()"
    private Assertion() {
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            DomainException.validationError(message);
        }
    }

    public static void notBlank(String string, String message){
        if(string == null || string.isBlank()){
            DomainException.validationError(message);
        }
    }




}
