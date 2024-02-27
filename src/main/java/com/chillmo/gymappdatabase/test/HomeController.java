package com.chillmo.gymappdatabase.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/api/home")
@RestController
public class HomeController {
    @GetMapping("/home")
    public String home(){
        return "Hello World";
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user")
    public String User(){
        return "Hello User";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public String Admin(){
        return "Hello Admin";
    }
}
