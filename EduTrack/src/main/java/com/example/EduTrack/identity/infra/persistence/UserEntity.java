package com.example.EduTrack.identity.infra.persistence;

import com.example.EduTrack.identity.domain.model.Email;
import com.example.EduTrack.identity.domain.model.Password;
import com.example.EduTrack.identity.domain.model.User;
import com.example.EduTrack.identity.domain.model.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "id",nullable = false,unique = true)
    private UUID id;

    @Column(name = "login",nullable = false,unique = true)
    private String login;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private UserRole role;

    @CreationTimestamp
    @Column(name = "createdTime",nullable = false)
    private Instant createdTime;

    @Column(name = "enabled",nullable = false)
    private Boolean enabled;

    public static UserEntity fromDomain(User user) {

        return new UserEntity(
                user.getId(),
                user.getLogin(),
                user.getPassword().value(),
                user.getEmail().address(),
                user.getRole(),
                user.getCreatedTime(),
                user.isEnabled()
        );
    }

    public User toDomain(){
        return new User(
                this.id,
                this.login,
                new Password(this.password),
                new Email(this.email),
                this.enabled,
                this.createdTime,
                this.role
        );
    }

}
