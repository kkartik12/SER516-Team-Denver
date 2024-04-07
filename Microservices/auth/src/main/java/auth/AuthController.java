package auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin()
@RequestMapping("/api") // Base path for API endpoints
public class AuthController {

    @Autowired
    AuthService authentication;

    @PostMapping("/login")
    @ResponseBody
    public AuthModel login(@RequestParam String username, @RequestParam String password) {
        try {
            AuthModel memberID = authentication.authenticate(username, password);
            if (memberID != null) {
                return memberID;
            } else {
                throw new RuntimeException("AuthModel is null");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error: ", e);
        }
    }

}