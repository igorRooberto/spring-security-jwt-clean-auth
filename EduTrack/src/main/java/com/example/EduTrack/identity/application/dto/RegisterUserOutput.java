package com.example.EduTrack.identity.application.dto;

import java.util.UUID;

public record RegisterUserOutput(UUID id,
                                 String login,
                                 String email
) {
}
