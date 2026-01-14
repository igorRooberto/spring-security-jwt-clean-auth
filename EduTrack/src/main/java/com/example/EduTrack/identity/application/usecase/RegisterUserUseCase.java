package com.example.EduTrack.identity.application.usecase;

import com.example.EduTrack.identity.application.dto.RegisterUserInput;
import com.example.EduTrack.identity.application.gateway.PasswordEncoder;
import com.example.EduTrack.identity.domain.model.Email;
import com.example.EduTrack.identity.domain.model.Password;
import com.example.EduTrack.identity.domain.model.User;
import com.example.EduTrack.identity.domain.repository.UserRepository;

public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void execute(RegisterUserInput input) {

        if (userRepository.existsByEmail(input.email())) {
            throw new RuntimeException("Este e-mail já está em uso.");
        }

        String passwordHash = passwordEncoder.encode(input.password());

        User newUser = new User(
                input.login(),
                new Password(passwordHash),
                new Email(input.email())
        );

        userRepository.save(newUser);
    }
}
