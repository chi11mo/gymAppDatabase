package com.chillmo.gymappdatabase.users.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users",
        uniqueConstraints = {
                //@UniqueConstraint(columnNames = "twitch"),
               // @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "email")
    private String email;
    @Column
    private String username;
    @Lob
    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Token token;

    public User() {

    }

    public User(String email, String password, Role role) {
        this.email = email;
        username = email;
        this.password = password;
        this.role = role;
    }
}
