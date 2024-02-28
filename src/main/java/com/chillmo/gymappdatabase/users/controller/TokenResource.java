package com.chillmo.gymappdatabase.users.controller;

import com.chillmo.gymappdatabase.users.domain.Token;
import com.chillmo.gymappdatabase.users.domain.TokenDto;
import com.chillmo.gymappdatabase.users.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tokens")
public class TokenResource {
    private final TokenService tokenService;
    @Autowired
    public TokenResource(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<TokenDto>> getAllTokens() {
        List<Token> tokens = tokenService.getAllTokens();
        List<TokenDto> tokenDtos = tokens.stream()
                .map(TokenDto::fromToken)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tokenDtos);
    }


}
