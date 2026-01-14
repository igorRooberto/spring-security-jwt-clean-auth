package com.example.EduTrack.identity.application.gateway;

public interface PasswordEncoder {

    String encode(String rawPassword);

    Boolean matches(String rawPassword, String encodedPassword);
}
