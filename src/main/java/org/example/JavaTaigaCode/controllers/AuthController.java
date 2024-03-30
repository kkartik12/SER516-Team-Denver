package org.example.JavaTaigaCode.controllers;

import org.example.JavaTaigaCode.service.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://54.89.25.90:8080"})
@RequestMapping("/api") // Base path for API endpoints
public class AuthController {

    @Autowired
    Authentication authentication;

        @PostMapping("/login")
        @ResponseBody
        public Integer login(@RequestParam String username, @RequestParam String password) {
            Integer memberID = authentication.authenticate(username, password);
            if(memberID != null) {
                return memberID;
            } else {
                return null;
            }
        }

}
