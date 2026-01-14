package com.example.EduTrack.identity.infra.service;
import com.example.EduTrack.identity.application.gateway.PasswordEncoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityPasswordEncoder implements PasswordEncoder {

    private final BCryptPasswordEncoder securityPasswordEncoder;

    public SecurityPasswordEncoder(BCryptPasswordEncoder securityPasswordEncoder) {
        this.securityPasswordEncoder = securityPasswordEncoder;
    }

    @Override
    public String encode(String rawPassword) {
        return securityPasswordEncoder.encode(rawPassword);
    }

    @Override
    public Boolean matches(String rawPassword, String encodedPassword) {
        return securityPasswordEncoder.matches(rawPassword, encodedPassword);
    }

}
