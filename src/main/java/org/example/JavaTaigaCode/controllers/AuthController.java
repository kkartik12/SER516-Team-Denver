package org.example.JavaTaigaCode.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api") // Base path for API endpoints
public class AuthController {

        @PostMapping("/login")
        @ResponseBody
        public Integer login(@RequestParam String username, @RequestParam String password) {
            Integer memberID = org.example.JavaTaigaCode.service.Authentication.authenticate(username, password);
            if(memberID != null) {
                return memberID;
            } else {
                return null;
            }
        }

}
