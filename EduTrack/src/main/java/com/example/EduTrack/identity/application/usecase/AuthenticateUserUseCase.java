package com.example.EduTrack.identity.application.usecase;

import com.example.EduTrack.identity.application.dto.AuthenticateUserInput;
import com.example.EduTrack.identity.application.dto.TokenResult;
import com.example.EduTrack.identity.application.gateway.PasswordEncoder;
import com.example.EduTrack.identity.application.gateway.TokenGateway;
import com.example.EduTrack.identity.domain.model.User;
import com.example.EduTrack.identity.domain.repository.UserRepository;

public class AuthenticateUserUseCase {

    private final UserRepository userRepository;
    private final TokenGateway tokenGateway;
    private final PasswordEncoder passwordEncoder;

    public AuthenticateUserUseCase(PasswordEncoder passwordEncoder, UserRepository userRepository, TokenGateway tokenGateway) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenGateway = tokenGateway;
    }

    public TokenResult execute(AuthenticateUserInput input) {
        User user = userRepository.findByLogin(input.login())
                .orElseThrow(() -> new RuntimeException("Credenciais inválidas"));

        if (!passwordEncoder.matches(input.password(), user.getPassword().value())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        var accessToken = tokenGateway.generateToken(user);
        var refreshToken = tokenGateway.generateRefreshToken(user);

        return new TokenResult(accessToken, refreshToken);
    }
}
