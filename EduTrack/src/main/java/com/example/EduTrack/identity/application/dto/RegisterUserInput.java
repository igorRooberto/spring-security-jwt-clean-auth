package com.example.EduTrack.identity.application.dto;

import com.example.EduTrack.identity.domain.model.UserRole;

public record RegisterUserInput(String login,
                                String email,
                                String password
                                ) {
}
