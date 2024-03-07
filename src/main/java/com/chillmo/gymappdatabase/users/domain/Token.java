package com.chillmo.gymappdatabase.users.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents a token entity for user authentication or other purposes.
 * Each token is uniquely associated with a user and can be used for actions like account verification or password reset.
 */
@Getter
@Setter
@Table(name = "token") // Specifies the table name in the database.
@Entity // Marks this class as a JPA entity.
public class Token {
    @Id // Marks this field as the primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies that the ID should be generated automatically.
    @Column(name = "id", nullable = false) // Maps this field to the "id" column in the "token" table.
    private Long id;

    @Column // Indicates that this field should be mapped to a column that has the same name.
    private String tokenContent; // Stores the actual token string.

    @Column(nullable = false)
    private LocalDateTime createdAt; // Stores the date and time when the token was created.

    @Column(nullable = false)
    private LocalDateTime expiresAt; // Stores the date and time when the token will expire.

    private LocalDateTime confirmedAt; // Stores the date and time when the token was used/confirmed.



    @OneToOne(fetch = FetchType.LAZY) // Specifies a one-to-one relationship with the User entity.
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    // Maps this relationship to the "user_id" column in the "token" table.
    @JsonIgnore // Indicates that this field should be ignored by JSON serialization and deserialization processes.
    private User user; // The user associated with this token.

    /**
     * Constructs a new Token with specified details.
     *
     * @param tokenContent The content of the token.
     * @param createdAt    The date and time when the token was created.
     * @param expiresAt    The date and time when the token expires.
     * @param user         The user associated with this token.
     */
    public Token(final String tokenContent, final LocalDateTime createdAt, final LocalDateTime expiresAt, final LocalDateTime confirmedAt, final User user) {
        this.tokenContent = tokenContent;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.confirmedAt = confirmedAt;
        this.user = user;
    }

    /**
     * Protected default constructor to ensure JPA entity integrity.
     */
    protected Token() {
        // Used by JPA.
    }
}
