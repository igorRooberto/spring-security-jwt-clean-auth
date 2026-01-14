package com.example.EduTrack.identity.infra.web;

import com.example.EduTrack.identity.application.dto.AuthenticateUserInput;
import com.example.EduTrack.identity.application.dto.RegisterUserInput;
import com.example.EduTrack.identity.application.dto.TokenResult;
import com.example.EduTrack.identity.application.gateway.TokenGateway;
import com.example.EduTrack.identity.application.usecase.AuthenticateUserUseCase;
import com.example.EduTrack.identity.application.usecase.RegisterUserUseCase;
import com.example.EduTrack.identity.domain.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase;

    public AuthController(AuthenticateUserUseCase authenticateUserUseCase, RegisterUserUseCase registerUserUseCase) {
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterUserInput request) {

       registerUserUseCase.execute(new RegisterUserInput(request.login(),
                                                         request.email(),
                                                         request.password(),
                                                         request.role()
               ));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResult> login(@RequestBody AuthenticateUserInput request){
        TokenResult tokens = authenticateUserUseCase.execute(new AuthenticateUserInput(request.login(), request.password()));

        return ResponseEntity.ok(tokens);
    }

}
