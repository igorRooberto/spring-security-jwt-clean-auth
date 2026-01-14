package com.example.EduTrack.identity.infra.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.EduTrack.identity.application.gateway.TokenGateway;
import com.example.EduTrack.identity.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenGenerator implements TokenGateway {

    @Value("${api.security.secret}")
    private String secret;

    @Override
    public String generateToken(User user) {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        try{
            return JWT.create()
                    .withIssuer("FinTrack-API")
                    .withSubject(user.getLogin())
                    .withClaim("ROLE", user.getRole().name())
                    .withClaim("id",user.getId().toString())
                    .withClaim("dateRegistration", user.getCreatedTime().toString())
                    .withClaim("email", user.getEmail().address())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        }
        catch (JWTCreationException ex){
            throw new JWTCreationException("ERROR WHILE GENERATIN TOKEN",ex);
        }
    }

    @Override
    public String generateRefreshToken(User userLogging){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try{
            return JWT.create()
                    .withIssuer("FinTrack-API")
                    .withSubject(userLogging.getLogin())
                    .withClaim("id",userLogging.getId().toString())
                    .withExpiresAt(genRefreshExpirationDate())
                    .sign(algorithm);
        }
        catch (JWTCreationException ex){
            throw new JWTCreationException("ERROR WHILE GENERATING REFRESH TOKEN",ex);
        }
    }

    private Instant genExpirationDate(){
        return Instant.now().plus(15, ChronoUnit.MINUTES);
    }

    private Instant genRefreshExpirationDate(){
        return Instant.now().plus(64, ChronoUnit.HOURS);
    }

}
