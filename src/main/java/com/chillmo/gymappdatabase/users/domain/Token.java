package com.chillmo.gymappdatabase.users.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * This class represents a Token.
 */
@Getter
@Setter
@Table(name = "token")
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String tokenContent;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    /**
     * Token constructor.
     *
     * @param tokenContent Token tokenContent
     * @param expiresAt    Token expiresAt
     * @param user         Token user
     */
    public Token(final String tokenContent, final LocalDateTime createdAt, final LocalDateTime expiresAt, final LocalDateTime confirmedAt, final User user) {
        this.tokenContent = tokenContent;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.confirmedAt = null;
        this.user = user;
    }

    protected Token() {


    }
}