package com.chillmo.gymappdatabase.users.controller;

import com.chillmo.gymappdatabase.mail.service.EmailService;
import com.chillmo.gymappdatabase.users.domain.Token;
import com.chillmo.gymappdatabase.users.domain.User;
import com.chillmo.gymappdatabase.users.repository.TokenRepository;
import com.chillmo.gymappdatabase.users.service.TokenServiceImpl;
import com.chillmo.gymappdatabase.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/signup")
public class SignUpResource {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TokenServiceImpl tokenService;
    private final TokenRepository tokenRepository;


    /**
     * Endpoint for registering a new user.
     * This method handles the POST request to register a new user. It checks if a user with the given email
     * already exists to prevent duplicate entries. If the user does not exist, it proceeds to save the new user
     * in the database. After successfully saving the user, it generates a verification email and sends it to the
     * user's email address. The email contains a link for the user to verify their account.
     *
     * @param user    The user object containing the new user's data. It is expected to be passed in the body
     *                of the POST request.
     * @param request The HttpServletRequest object, used here to extract the URL for creating the verification
     *                link sent in the verification email.
     * @return A ResponseEntity object containing the newly created user and the HTTP status. Returns BAD_REQUEST
     * if the user already exists, otherwise CREATED upon successful registration.
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user, HttpServletRequest request) {
        if (userService.findUserByEmail(user.getEmail()) != null) {
            // User already exists, handle accordingly
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User savedUser = userService.registerUser(user);
        String siteURL = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
        emailService.sendVerificationEmail(savedUser, siteURL);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    /**
     * Checks if a given token is active.
     *
     * @param token the token that is requested to be checked
     * @return If the token is accepted or dismissed as string
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    @PutMapping("/validate/isTokenActive")
    public ResponseEntity<String> isTokenActive(@RequestParam final String token) {

        if (tokenService.findTokenByContent(token).getConfirmedAt() != null) {
            return new ResponseEntity<>("ACCEPTED", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("DISMISSED", HttpStatus.OK);
        }
    }

    /**
     * Methode to send a Validation mail again.
     *
     * @param token token of the given User
     * @return HttpStatus.OK
     */
    @SuppressWarnings("PMD.LocalVariableCouldBeFinal")
    @PutMapping("/validateAgain")
    public ResponseEntity<String> sendValidationMailAgain(@RequestParam final String token) {
        User user = tokenService.findUserByToken(token);
        final Token tokenByContent = tokenService.findTokenByContent(token);
        tokenService.increaseExpiredTime(tokenByContent);
        // mailService.sendValidationMail(user.getEMail(), tokenByContent);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
