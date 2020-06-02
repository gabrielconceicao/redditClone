package br.com.clone.reddit.controller;

import br.com.clone.reddit.dto.RegisterRequest;
import br.com.clone.reddit.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> sign(@RequestBody RegisterRequest registerRequest) {
        authService.sign(registerRequest);
        return new ResponseEntity<>("User registration successful", HttpStatus.OK);
    }

}
