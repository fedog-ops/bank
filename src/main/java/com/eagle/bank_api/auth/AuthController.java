package com.eagle.bank_api.auth;

import com.eagle.bank_api.error.UnauthorizedException;
import com.eagle.bank_api.security.JWTService;
import com.eagle.bank_api.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final UserService userService;
    private final JWTService jwtService;

    public AuthController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthDto request) {
      boolean isValidUser = userService.existsByEmail(request.getEmail());

        if (!isValidUser) {
            throw new UnauthorizedException("Unauthorized");
        }

        String token = jwtService.generateToken(request.getEmail());

        return ResponseEntity.ok(token);
    };
}
