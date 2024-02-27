package com.chillmo.gymappdatabase.users.service;

import com.chillmo.gymappdatabase.users.controller.SecurityUser;
import com.chillmo.gymappdatabase.users.domain.Token;
import com.chillmo.gymappdatabase.users.domain.User;
import com.chillmo.gymappdatabase.users.repository.TokenRepository;
import com.chillmo.gymappdatabase.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;

    private final TokenServiceImpl tokenServiceImpl;

    private final TokenRepository tokenRepository;


    /**
     * Service method to add {@link User} to register
     * and add Twitch profile img to it.
     *
     * @param user {@link User} to add it to User list.
     * @return the added User .
     */
    public User addUser(User user) {


        final String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        //Todo send confirmation Token and sending email
        //tokenServiceImpl.generateToken(user);
        return user;

    }

    /**
     * Service method to get all registered {@link User}.
     *
     * @return a List of all {@link User}.
     */
    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    /**
     * Service method to find a specific {@link User} by id.
     *
     * @param id for identify the searched {@link User}.
     * @return the searched {@link User}.
     */
    public User findUserByID(Long id) {
        return userRepository.findUserById(id);
    }

    /**
     * Service method to delete a {@link User} by Id.
     *
     * @param id id for identify the searched {@link User}.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Service method to update a {@link User}
     *
     * @param newUser new {@link User} to updated.
     * @return updated {@link User}.
     */
    public User updateUser(final User newUser) {
        final User oldUser = userRepository.findUserById(newUser.getId());

        newUser.setId(oldUser.getId());

        return userRepository.save(newUser);
    }


    /**
     * Service method to reset the given Password for the {@link User}.
     *
     * @param token       {@link Token}.
     * @param newPassword the one.
     * @return response.
     */
    public String resetPassword(final String token, final String newPassword) {
        final User user = tokenServiceImpl.getUserByToken(token);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "SUCCESS";
    }

    /**
     * @param token
     * @return
     */
    public boolean checkForToken(final String token) {
        final Token tokenByTokenContent = tokenRepository.findTokenByTokenContent(token);
        final User user = findUserByToken(token);

        if (tokenByTokenContent.getExpiresAt().isBefore(LocalDateTime.now())) {
            //logger.info("Token abgelaufen");
            return false;
        } else {
            setUserIsEnabled(user);
            // logger.info("User wurde aktiviert");
            return true;
        }
    }

    /**
     * Service method to activated a {@link User}.
     *
     * @param user {@link User}/
     */
    public void setUserIsEnabled(final User user) {
        // user.setIsEnabled(true);
        userRepository.save(user);

    }

    /**
     * Service method to find a {@link User} by token.
     *
     * @param tokenContent for identify the searched {@link User}.
     * @return the searched {@link User}.
     */
    public User findUserByToken(final String tokenContent) {
        return userRepository.findUserById(tokenRepository.findTokenByTokenContent(tokenContent).getUser().getId());
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findUserByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
/*
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByeMail(username);
    }
    */
}
