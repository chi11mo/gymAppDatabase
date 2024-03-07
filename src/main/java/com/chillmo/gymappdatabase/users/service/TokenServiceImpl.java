package com.chillmo.gymappdatabase.users.service;

import com.chillmo.gymappdatabase.users.domain.Token;
import com.chillmo.gymappdatabase.users.domain.User;
import com.chillmo.gymappdatabase.users.repository.TokenRepository;
import com.chillmo.gymappdatabase.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {
    /**
     * Public field that gives a value for expiring the token.
     */
    @SuppressWarnings("PMD.FinalFieldCouldBeStatic")
    public static final int expiredAtUpdate = 20;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;


    /**
     * Generates a new authentication token for the specified user.
     *
     * @param user the user for whom the token is generated
     * @return the newly generated authentication token
     */
    @Override
    public Token generateToken(final User user) {
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(expiredAtUpdate);
        LocalDateTime createdAt = LocalDateTime.now();

        String tokenContent = UUID.randomUUID().toString();

        while (tokenRepository.findTokenByTokenContent(tokenContent) != null) {
            tokenContent = UUID.randomUUID().toString();
        }
        Token token = new Token(tokenContent, createdAt, expiresAt, null, user);
        user.setToken(token); // Link the token to the user
        return tokenRepository.save(token);
    }


    @Override
    public Token findTokenByContent(final String content) {
        return tokenRepository.findTokenByTokenContent(content);
    }

    @Override
    public void increaseExpiredTime(final Token token) {
        token.setExpiresAt(LocalDateTime.now().plusMinutes(expiredAtUpdate));
        tokenRepository.save(token);
    }


    @Override
    public User getUserByToken(final String tokenContent) {
        return tokenRepository.findTokenByTokenContent(tokenContent).getUser();
    }

    @Override
    public Token getTokenByUser(final User user) {
        return tokenRepository.findTokenByUser(user);
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
     * This Method verifies the given token.
     *
     * @param tokenContent given token content.
     * @return boolean is token confirmed.
     */
    public boolean verifyToken(String tokenContent) {
        Token tokenToVerify = tokenRepository.findTokenByTokenContent(tokenContent);
        if (tokenToVerify == null) {
            return false; // Token does not exist
        }

        if (tokenToVerify.getExpiresAt().isBefore(LocalDateTime.now())) {
            return false; // Token has expired
        } else {
            tokenToVerify.setConfirmedAt(LocalDateTime.now());
            tokenRepository.save(tokenToVerify);
            return true; // Token is valid if it has not been confirmed/used yet
        }

    }

    @Override
    public List<Token> getAllTokens() {
        return (List<Token>) tokenRepository.findAll();
    }


}
