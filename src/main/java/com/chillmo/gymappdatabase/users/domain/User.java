package com.chillmo.gymappdatabase.users.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a user entity within the application.
 * This class is annotated with JPA (Java Persistence API) annotations to define the table mapping for the User entity and its fields.
 */
@Setter // Lombok annotation to generate setters for all fields.
@Getter // Lombok annotation to generate getters for all fields.
@Entity // Specifies that this class is an entity and is mapped to a database table.
@Table(name = "users", // Defines the table name for this entity.
        uniqueConstraints = {
                // Define unique constraints for the table. Uncomment or add constraints as necessary.
                // @UniqueConstraint(columnNames = "twitch"),
                // @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id // Marks the id field as the primary key.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Configures the way of increment of the specified column(field).
    @Column(name = "id", nullable = false)
    // Maps the id field to the column named "id" in the "users" table. It cannot be null.
    private long id;

    @Column(name = "email") // Maps the email field to the column named "email" in the "users" table.
    private String email;

    @Column // The default column name is the same as the field name (username).
    private String username;

    @Lob // Specifies that the field should be persisted as a large object to a database-supported large object type.
    @Column // The default column name is the same as the field name (password).
    private String password;

    @Enumerated(EnumType.STRING) // Specifies that the enum should be persisted as a string.
    private Role role; // The role of the user, represented by an enum.

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    // Establishes a one-to-one relationship with the Token entity. The 'mappedBy' attribute indicates that
    // the User entity does not control the relationship (it's the inverse side).
    private Token token;

    /**
     * Default constructor.
     */
    public User() {

    }

    /**
     * Parametrized constructor for creating a new user with the specified email, password, and role.
     *
     * @param email    The email of the user.
     * @param password The password of the user.
     * @param role     The role of the user.
     */
    public User(String email, String password, Role role) {
        this.email = email;
        this.username = email; // In this setup, the username is initially set to the email.
        this.password = password;
        this.role = role;
    }
}
