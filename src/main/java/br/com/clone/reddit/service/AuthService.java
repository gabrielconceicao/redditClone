package br.com.clone.reddit.service;

import br.com.clone.reddit.dto.AuthenticationResponse;
import br.com.clone.reddit.dto.LoginRequest;
import br.com.clone.reddit.dto.RegisterRequest;
import br.com.clone.reddit.exceptions.SpringRedditException;
import br.com.clone.reddit.model.NotificationEmail;
import br.com.clone.reddit.model.User;
import br.com.clone.reddit.model.VerificationToken;
import br.com.clone.reddit.repository.UserRepository;
import br.com.clone.reddit.repository.VerificationTokenRepository;
import br.com.clone.reddit.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public void sign(RegisterRequest registerRequest) throws SpringRedditException {

        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent())
            throw new SpringRedditException("Username already exists");


        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail(
                "Please activate your Account",
                user.getEmail(),
                "Thank you for signing up to Spring Reddit, please click on the below url to +" +
                        "activate your account http://localhost:8080/api/auth/accountVerification/" + token
        ));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));

        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        User user = userRepository.findByUsername(verificationToken.getUser().getUsername())
                .orElseThrow(() -> new SpringRedditException("User not found"));

        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);

        return new AuthenticationResponse(token, loginRequest.getUsername());
    }
}
