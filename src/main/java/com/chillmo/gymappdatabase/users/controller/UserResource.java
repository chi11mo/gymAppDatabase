package com.chillmo.gymappdatabase.users.controller;


import com.chillmo.gymappdatabase.mail.service.EmailService;
import com.chillmo.gymappdatabase.users.domain.Token;
import com.chillmo.gymappdatabase.users.domain.User;
import com.chillmo.gymappdatabase.users.repository.TokenRepository;
import com.chillmo.gymappdatabase.users.service.TokenServiceImpl;
import com.chillmo.gymappdatabase.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The UserResource class is a REST controller that defines the API endpoints for user-related operations.
 * It allows operations such as retrieving all users, finding a user by ID, updating, deleting, and registering users,
 * as well as verifying user tokens and resetting passwords.
 */
@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TokenServiceImpl tokenService;
    private final TokenRepository tokenRepository;

    /**
     * Constructs a new UserResource with the necessary services and repositories.
     *
     * @param userService     The service to handle user operations.
     * @param passwordEncoder The encoder for hashing passwords.
     * @param tokenService    The service for handling token-related operations.
     * @param emailService    The service for sending emails.
     * @param tokenRepository The repository for accessing token data.
     */
    public UserResource(UserService userService, final PasswordEncoder passwordEncoder,
                        final TokenServiceImpl tokenService, final EmailService emailService, TokenRepository tokenRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return A ResponseEntity containing a list of all users and HTTP status OK.
     */
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> user = userService.findAllUsers();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        try {
            // Attempt to find the user by ID
            User user = userService.findUserByID(id);

            // Check if the user object is null
            if (user == null) {
                // User not found, return 404 Not Found
                return new ResponseEntity<>("User not found with ID: " + id, HttpStatus.NOT_FOUND);
            }

            // User found, return 200 OK with the user data
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            // In case of unexpected server errors, return 500 Internal Server Error
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updateUser = userService.registerUser(user);
        userService.updateUser(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This Method implements API Layer for authenticating an existing {@link User}.
     *
     * @param username username of  {@link User}
     * @param password password of  {@link User}
     * @return the status of the HTTP Status.

     @PostMapping("/authenticate") public ResponseEntity<String> authenticate(
     @RequestParam final String username,
     @RequestParam final String password
     ) {
     final var user = userService.findUserByeMail(username);

     if (user != null && passwordEncoder.matches(password, user.getPassword())) {
     return new ResponseEntity<>("DUMMY_TOKEN", HttpStatus.OK);
     }
     return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
     }
     */
    /**
     * This Method implements API Layer to reset the password of a user corresponding to a token.
     *
     * @param token       the Token corresponding to a unique User, whose Password is supposed to be reset.
     * @param newPassword the new Password of the User.
     * @return the status of the HTTP Status.
     */
    @PutMapping("/reset/password")
    public ResponseEntity<String> resetPassword(@RequestParam final String token,
                                                @RequestParam final String newPassword) {
        return new ResponseEntity<>(userService.resetPassword(token, newPassword), HttpStatus.OK);
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

        if (userService.checkForToken(token)) {
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
        User user = userService.findUserByToken(token);
        final Token tokenByContent = tokenService.findTokenByContent(token);
        tokenService.increaseExpiredTime(tokenByContent);
        // mailService.sendValidationMail(user.getEMail(), tokenByContent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

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
    @PostMapping("/signup")
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


    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("token") String token) {
        Token foundToken = tokenService.findTokenByContent(token);

        if (foundToken != null) {
            foundToken.setConfirmedAt(LocalDateTime.now());
            tokenRepository.save(foundToken);
            return ResponseEntity.ok("Token verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
    }


/**
 * This Method implements API Layer for sending a Mail to Reset the password {@link User}.
 *
 * @param emailAddress email address of the user {@link User}
 * @return the status of the HTTP Status.
 * @SuppressWarnings("PMD.ConfusingTernary")
 * @PutMapping("/reset") public ResponseEntity<String> sendResetMail(@RequestParam final String emailAddress) {
 * final User user = userService.findUserByeMail(emailAddress);
 * if (user != null) {
 * // mailService.sendResetMail(emailAddress);
 * return new ResponseEntity<>(HttpStatus.OK);
 * } else {
 * return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
 * }
 * }
 */
}

