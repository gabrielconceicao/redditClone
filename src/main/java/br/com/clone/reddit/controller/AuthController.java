package br.com.clone.reddit.controller;

import br.com.clone.reddit.dto.RegisterRequest;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/signup")
    public RequestEntity<String> sign(@RequestBody RegisterRequest registerRequest) {
        return null;
    }

}
