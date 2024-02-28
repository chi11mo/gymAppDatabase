package com.chillmo.gymappdatabase.users.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class TokenDto {
    private Long id;
    private String tokenContent;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private String username; // Only include the username from the User entity

    // Constructors, getters, and setters
    public TokenDto(Long id, String tokenContent, LocalDateTime createdAt, LocalDateTime expiresAt, String username) {
        this.id = id;
        this.tokenContent = tokenContent;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.username = username;
    }

    public static TokenDto fromToken(Token token) {
        return new TokenDto(
                token.getId(),
                token.getTokenContent(),
                token.getCreatedAt(),
                token.getExpiresAt(),
                token.getUser().getUsername()
        );
    }
}
