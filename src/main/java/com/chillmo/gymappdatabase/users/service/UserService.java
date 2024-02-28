package com.chillmo.gymappdatabase.users.service;

import com.chillmo.gymappdatabase.users.domain.Role;
import com.chillmo.gymappdatabase.users.domain.SecurityUser;
import com.chillmo.gymappdatabase.users.domain.Token;
import com.chillmo.gymappdatabase.users.domain.User;
import com.chillmo.gymappdatabase.users.repository.TokenRepository;
import com.chillmo.gymappdatabase.users.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Provides user-related services such as registering users, finding users, and password management.
 * Implements UserDetailsService for Spring Security integration, enabling user authentication and authorization.
 */
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenServiceImpl tokenServiceImpl;
    private final TokenRepository tokenRepository;
    private EntityManager entityManager;

    /**
     * Registers a new user by encoding their password, setting their role, and saving them to the database.
     * Additionally, generates a registration token for email verification.
     *
     * @param user User to be registered.
     * @return The registered user with a verification token.
     */
    public User registerUser(User user) {
        final String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setUsername(user.getEmail());
        user.setRole(Role.USER);
        userRepository.save(user);
        Token token = tokenServiceImpl.generateToken(user);
        user.setToken(token);
        return user;
    }

    /**
     * Retrieves all registered users.
     *
     * @return A list of all users.
     */
    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    /**
     * Finds a user by their ID.
     *
     * @param id ID of the user to find.
     * @return The found user or null if not found.
     */
    public User findUserByID(Long id) {
        return userRepository.findUserById(id);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id ID of the user to delete.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Updates a user with new information.
     *
     * @param newUser The new user information to update.
     * @return The updated user.
     */
    public User updateUser(final User newUser) {
        final User oldUser = userRepository.findUserById(newUser.getId());
        newUser.setId(oldUser.getId());
        return userRepository.save(newUser);
    }

    /**
     * Resets a user's password given a token and new password.
     *
     * @param token       The token indicating a valid password reset request.
     * @param newPassword The new password to set for the user.
     * @return A success message.
     */
    public String resetPassword(final String token, final String newPassword) {
        final User user = tokenServiceImpl.getUserByToken(token);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "SUCCESS";
    }

    /**
     * Checks if a token is valid and if so, activates the corresponding user.
     *
     * @param token The token to check.
     * @return true if the token is valid and the user is activated, false otherwise.
     */
    public boolean checkForToken(final String token) {
        final Token tokenByTokenContent = tokenRepository.findTokenByTokenContent(token);
        final User user = findUserByToken(token);
        if (tokenByTokenContent.getExpiresAt().isBefore(LocalDateTime.now())) {
            return false;
        } else {
            setUserIsEnabled(user);
            return true;
        }
    }

    /**
     * Activates a user account.
     *
     * @param user The user to activate.
     */
    public void setUserIsEnabled(final User user) {
        userRepository.save(user);
    }

    /**
     * Finds a user associated with a specific token.
     *
     * @param tokenContent The content of the token.
     * @return The user associated with the token.
     */
    public User findUserByToken(final String tokenContent) {
        return userRepository.findUserById(tokenRepository.findTokenByTokenContent(tokenContent).getUser().getId());
    }

    /**
     * Loads a user by username for authentication purposes.
     *
     * @param username The username of the user to load.
     * @return UserDetails of the loaded user.
     * @throws UsernameNotFoundException If the user cannot be found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findUserByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Service method to find a {@link User} by their email address.
     * This method uses the {@link UserRepository} to search for a user with the given email.
     * If a user with the specified email exists in the database, that user is returned.
     *
     * @param email The email address to search for in the user repository.
     * @return The {@link User} object that matches the given email if found, null otherwise.
     */
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


}
