package com.chillmo.gymappdatabase.users.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users",
        uniqueConstraints = {
                //@UniqueConstraint(columnNames = "twitch"),
                @UniqueConstraint(columnNames = "username")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    private String username;

    @Lob
    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(){

    }

    public User(String username, String password, Role role){
        this.username = username;
        this.password = password;
        this.role = Role.USER;
    }
}
