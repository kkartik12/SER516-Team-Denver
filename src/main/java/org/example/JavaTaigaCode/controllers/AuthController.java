package org.example.JavaTaigaCode.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api") // Base path for API endpoints
public class AuthController {

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password) {
        String authToken = org.example.JavaTaigaCode.service.Authentication.authenticate(username, password);
        if(authToken != null) {
            return authToken;
        } else {
            return "Invalid Credentials";
        }
    }

}
