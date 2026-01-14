package com.example.EduTrack.identity.application.gateway;

import com.example.EduTrack.identity.domain.model.User;

public interface TokenGateway {

    String generateToken(User user);
    String generateRefreshToken(User user);
}
