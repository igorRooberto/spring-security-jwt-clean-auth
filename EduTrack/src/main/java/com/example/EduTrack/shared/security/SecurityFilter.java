package com.example.EduTrack.shared.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.EduTrack.identity.domain.model.Email;
import com.example.EduTrack.identity.domain.model.Password;
import com.example.EduTrack.identity.domain.model.User;
import com.example.EduTrack.identity.domain.model.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenVerifier tokenVerifier;

    public SecurityFilter(TokenVerifier tokenVerifier) {
        this.tokenVerifier = tokenVerifier;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = recoverToken(request);

        if(token != null){
            authenticateClient(token);
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var auth = request.getHeader("Authorization");
        if(auth == null) return null;
        return auth.replace("Bearer", "").trim();
    }

    private void authenticateClient(String token) {
        Optional.ofNullable(tokenVerifier.validateToken(token))
                .filter(jwt ->jwt.getSubject() != null && !jwt.getSubject().isBlank())
                .filter(jwt -> !jwt.getClaim("ROLE").isMissing())
                .filter(jwt -> !jwt.getClaim("ROLE").isNull())
                .filter(jwt -> !jwt.getClaim("id").isMissing())
                .filter(jwt -> !jwt.getClaim("id").isNull())
                .ifPresent(this::setSecurityContext);
    }

    private void setSecurityContext(DecodedJWT jwt) {
        String login = jwt.getSubject();
        String role = jwt.getClaim("ROLE").asString();
        Instant createdTime = Instant.parse(jwt.getClaim("dateRegistration").asString());
        String email = jwt.getClaim("email").asString();
        String idString = jwt.getClaim("id").asString();
        UUID id = UUID.fromString(idString);

        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

        User user = new User(id,
                            login,
                            new Password("TOKEN_AUTHENTICATED"),
                            new Email(email),
                            true,
                            createdTime,
                            UserRole.valueOf(role)
                );

        var auth = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
