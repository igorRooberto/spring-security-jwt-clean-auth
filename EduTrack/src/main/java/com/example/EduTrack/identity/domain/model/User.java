package com.example.EduTrack.identity.domain.model;

import com.example.EduTrack.shared.domain.validation.Assertion;

import java.time.Instant;
import java.util.UUID;

public class User {

    private final UUID id;
    private String login;
    private Password password;
    private Email email;
    private boolean enabled;
    private UserRole role;
    private final Instant createdTime;

    public User(String login, Password password, Email email) {
        Assertion.notBlank(login, "O login é obrigatório.");
        Assertion.notNull(password, "A senha é obrigatória.");
        Assertion.notNull(email, "O e-mail é obrigatório.");

        this.id = UUID.randomUUID();
        this.login = login;
        this.password = password;
        this.email = email;
        this.enabled = true;
        this.createdTime = Instant.now();
        this.role = UserRole.USER;
    }

    public User(UUID id, String login, Password password, Email email, boolean enabled, Instant createdTime,UserRole role) {
        Assertion.notNull(id, "O ID do usuário não pode ser nulo.");
        Assertion.notBlank(login, "O login é obrigatório.");
        Assertion.notNull(password, "A senha é obrigatória.");
        Assertion.notNull(email, "O e-mail é obrigatório.");
        Assertion.notNull(createdTime, "A data de criação é obrigatória.");

        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.createdTime = createdTime;
        this.role = role;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public Email getEmail() {
        return email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public UUID getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public Password getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public void active(){
        this.enabled = true;
    }

    public void disable(){
        this.enabled = false;
    }

    public void changePassword(Password newPassword){
        Assertion.notNull(newPassword, "A nova Senha não pode ser Nula");

        this.password = newPassword;
    }

}
