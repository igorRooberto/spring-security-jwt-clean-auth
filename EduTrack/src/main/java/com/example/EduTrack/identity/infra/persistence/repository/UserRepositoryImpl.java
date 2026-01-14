package com.example.EduTrack.identity.infra.persistence.repository;

import com.example.EduTrack.identity.domain.model.User;
import com.example.EduTrack.identity.domain.repository.UserRepository;
import com.example.EduTrack.identity.infra.persistence.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.fromDomain(user);

        UserEntity savedEntity = jpaUserRepository.save(entity);

        return savedEntity.toDomain();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        Optional<UserEntity> entity = jpaUserRepository.findByLogin(login);

        return entity.map(UserEntity::toDomain);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }
}
