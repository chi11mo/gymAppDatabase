package com.chillmo.gymappdatabase.users.controller;


import com.chillmo.gymappdatabase.users.domain.Token;
import com.chillmo.gymappdatabase.users.domain.User;
import com.chillmo.gymappdatabase.users.service.TokenServiceImpl;
import com.chillmo.gymappdatabase.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final TokenServiceImpl tokenService;


    public UserResource(UserService userService, final PasswordEncoder passwordEncoder,
                        final TokenServiceImpl tokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> user = userService.findAllUsers();
        return new ResponseEntity<List<User>>(user, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.findUserByID(id);
        // TODO: When user was not found need Response
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updateUser = userService.addUser(user);
        userService.updateUser(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:4200")
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
     * Methode to send a Validationmail again.
     *
     * @param token token of the given User
     * @return HttpStatus.OK
     */
    @SuppressWarnings("PMD.LocalVariableCouldBeFinal")
    @PutMapping("/validateAgain")
    public ResponseEntity<String> sendValidationMailAgain(@RequestParam final String token) {
        User user = userService.findUserByToken(token);
        final Token tokenbyContent = tokenService.findTokenByContent(token);
        tokenService.increaseExpiredTime(tokenbyContent);
        // mailService.sendValidationMail(user.getEMail(), tokenbyContent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This Method implements API Layer for sending a Mail to Reset the password {@link User}.
     *
     * @param emailAddress email address of the user {@link User}
     * @return the status of the HTTP Status.

     @SuppressWarnings("PMD.ConfusingTernary")
     @PutMapping("/reset") public ResponseEntity<String> sendResetMail(@RequestParam final String emailAddress) {
     final User user = userService.findUserByeMail(emailAddress);
     if (user != null) {
     // mailService.sendResetMail(emailAddress);
     return new ResponseEntity<>(HttpStatus.OK);
     } else {
     return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
     }
     }
     */
}

