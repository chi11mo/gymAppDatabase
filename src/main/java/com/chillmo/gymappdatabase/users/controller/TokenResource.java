package com.chillmo.gymappdatabase.users.controller;

import com.chillmo.gymappdatabase.users.domain.Token;
import com.chillmo.gymappdatabase.users.domain.TokenDTO;
import com.chillmo.gymappdatabase.users.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
