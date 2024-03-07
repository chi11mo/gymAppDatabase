package com.chillmo.gymappdatabase.users.controller;

import com.chillmo.gymappdatabase.users.domain.Token;
import com.chillmo.gymappdatabase.users.domain.TokenDTO;
import com.chillmo.gymappdatabase.users.domain.User;
import com.chillmo.gymappdatabase.users.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/tokens")
public class TokenResource {
    private final TokenService tokenService;


    @GetMapping("/all")
    public ResponseEntity<List<TokenDTO>> getAllTokens() {
        List<Token> tokens = tokenService.getAllTokens();
        List<TokenDTO> tokenDTOS = tokens.stream()
                .map(TokenDTO::fromToken)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tokenDTOS);
    }

    /**
     * Checks if a given token is active.
     *
     * @param token the token that is requested to be checked
     * @return ResponseEntity with the status of the token (ACCEPTED, DISMISSED, or NOT FOUND)
     */
    @GetMapping("/validate/isTokenActive")
    public ResponseEntity<String> isTokenActive(@RequestParam String token) {
        Token foundToken = tokenService.findTokenByContent(token);

        // Check if the token was found
        if (foundToken == null) {
            // Token not found in the database
            return new ResponseEntity<>("TOKEN NOT FOUND", HttpStatus.NOT_FOUND);
        } else if (foundToken.getConfirmedAt() != null) {
            // Token is found and confirmed
            return new ResponseEntity<>("ACCEPTED", HttpStatus.OK);
        } else {
            // Token is found but not confirmed
            return new ResponseEntity<>("DISMISSED", HttpStatus.OK);
        }
    }

    /**
     * Endpoint to verify the given token.
     *
     * @param token the token to be verified
     * @return ResponseEntity with verification result
     */
    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestParam String token) {
        Token tokenToVerify = tokenService.findTokenByContent(token);

        if (tokenToVerify == null) {
            return new ResponseEntity<>("TOKEN NOT FOUND", HttpStatus.NOT_FOUND);
        }

        boolean isTokenValid = tokenService.verifyToken(token);

        if (isTokenValid) {
            // Token is valid
            return new ResponseEntity<>("TOKEN CONFIRMED", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("TOKEN EXPIRED", HttpStatus.GONE);
        }
    }

    /**
     * Methode to send a Validation mail again.
     *
     * @param token token of the given User
     * @return HttpStatus.OK
     @SuppressWarnings("PMD.LocalVariableCouldBeFinal")
     @PutMapping("/validateAgain") public ResponseEntity<String> sendValidationMailAgain(@RequestParam final String token) {
     User user = tokenService.findUserByToken(token);
     final Token tokenByContent = tokenService.findTokenByContent(token);
     tokenService.increaseExpiredTime(tokenByContent);
     // mailService.sendValidationMail(user.getEMail(), tokenByContent);
     return new ResponseEntity<>(HttpStatus.OK);
     }

     */
}
