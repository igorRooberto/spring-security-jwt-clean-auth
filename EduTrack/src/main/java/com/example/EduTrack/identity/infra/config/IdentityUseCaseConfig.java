package com.example.EduTrack.identity.infra.config;

import com.example.EduTrack.identity.application.gateway.PasswordEncoder;
import com.example.EduTrack.identity.application.gateway.TokenGateway;
import com.example.EduTrack.identity.application.usecase.AuthenticateUserUseCase;
import com.example.EduTrack.identity.application.usecase.RegisterUserUseCase;
import com.example.EduTrack.identity.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdentityUseCaseConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGateway tokenGateway;

    public IdentityUseCaseConfig(PasswordEncoder passwordEncoder, UserRepository userRepository, TokenGateway tokenGateway) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenGateway = tokenGateway;
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase() {
        return new RegisterUserUseCase(this.passwordEncoder,this.userRepository);
    }

    @Bean
    public AuthenticateUserUseCase authenticateUserUseCase() {
        return new AuthenticateUserUseCase(this.passwordEncoder,this.userRepository, this.tokenGateway);
    }

}
