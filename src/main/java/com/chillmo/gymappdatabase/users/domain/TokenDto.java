package com.chillmo.gymappdatabase.users.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for Token entity.
 * This class is used to transfer token data in a more controlled way, especially over the network.
 * It only includes essential fields required for specific operations or responses, enhancing data encapsulation and API security.
 */
@Getter // Lombok annotation to generate getters for all fields.
@Setter // Lombok annotation to generate setters for all fields.
public class TokenDto {
    private Long id; // The unique identifier of the token.
    private String tokenContent; // The actual string content of the token.
    private LocalDateTime createdAt; // The date and time when the token was created.
    private LocalDateTime expiresAt; // The date and time when the token expires.
    private String username; // The username of the user associated with this token.

    /**
     * Constructs a new TokenDto with specified details.
     *
     * @param id           The unique identifier of the token.
     * @param tokenContent The actual string content of the token.
     * @param createdAt    The creation date and time of the token.
     * @param expiresAt    The expiration date and time of the token.
     * @param username     The username of the associated user.
     */
    public TokenDto(Long id, String tokenContent, LocalDateTime createdAt, LocalDateTime expiresAt, String username) {
        this.id = id;
        this.tokenContent = tokenContent;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.username = username;
    }

    /**
     * Converts a Token entity to a TokenDto.
     * This static method allows for easy conversion from Token entity instances to TokenDto instances.
     *
     * @param token The Token entity to convert.
     * @return A new TokenDto instance populated with data from the given Token entity.
     */
    public static TokenDto fromToken(Token token) {
        return new TokenDto(
                token.getId(),
                token.getTokenContent(),
                token.getCreatedAt(),
                token.getExpiresAt(),
                token.getUser().getUsername() // Extracts the username from the associated User entity.
        );
    }
}
