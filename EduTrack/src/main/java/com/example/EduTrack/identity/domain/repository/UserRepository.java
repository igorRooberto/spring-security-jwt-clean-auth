package com.example.EduTrack.identity.domain.repository;

import com.example.EduTrack.identity.domain.model.Email;
import com.example.EduTrack.identity.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByLogin(String login);

    Boolean existsByEmail(String email);
}
