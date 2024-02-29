package com.db.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.backend.dto.CurrentUserResponseDTO;
import com.db.backend.dto.UserAuthenticationRequestDTO;
import com.db.backend.dto.UserRegistrationRequestDTO;
import com.db.backend.dto.UserRegistrationResponseDTO;
import com.db.backend.entity.User;
import com.db.backend.infra.security.JwtService;
import com.db.backend.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserAuthenticationRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = this.jwtService.generate((User) auth.getPrincipal());
        return ResponseEntity.ok(new UserRegistrationResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserRegistrationRequestDTO data) {
        if (this.repository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.email(), encryptedPassword);
        this.repository.save(newUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String email = user.getEmail();
        Long id = user.getId();
        return ResponseEntity.ok(new CurrentUserResponseDTO(id, email));
    }
}
