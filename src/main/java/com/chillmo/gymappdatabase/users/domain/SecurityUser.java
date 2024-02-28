package com.chillmo.gymappdatabase.users.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * SecurityUser is a custom implementation of Spring Security's UserDetails interface.
 * This class adapts the User entity to work with Spring Security for authentication and authorization.
 */
public class SecurityUser implements UserDetails {

    // Reference to the User entity this UserDetails is based on
    private final User user;

    /**
     * Constructor that takes a User entity and uses it to create a new instance of SecurityUser.
     *
     * @param user the User entity this SecurityUser is based on.
     */
    public SecurityUser(final User user) {
        this.user = user;
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return a collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converts the User's role to a Spring Security GrantedAuthority
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
    }

    /**
     * Returns the password used for authenticating the user.
     *
     * @return the password string of the user.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username used to authenticate the user. In this case, it's the email.
     *
     * @return the username (email) of the user.
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
     *
     * @return true if the user's account is valid (non-expired), false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
     *
     * @return true if the user is not locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired credentials prevent authentication.
     *
     * @return true if the user's credentials are valid (non-expired), false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
     *
     * @return true if the user is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
