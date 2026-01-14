package com.example.EduTrack.identity.application.dto;

public record AuthenticateUserOutput(String accessToken,
                                     String refreshToken
) {
}
