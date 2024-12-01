package com.depoisdosim.depoisdosim.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.depoisdosim.depoisdosim.domain.user.AuthenticationDTO;
import com.depoisdosim.depoisdosim.domain.user.LoginResponseDTO;
import com.depoisdosim.depoisdosim.domain.user.RegisterDTO;
import com.depoisdosim.depoisdosim.models.User;
import com.depoisdosim.depoisdosim.repositories.UserRepository;
import com.depoisdosim.depoisdosim.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth") 
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        Long id = ((User) auth.getPrincipal()).getId();
        Long weddingId = 0L; // Definindo o valor padrão para o weddingId como 0

        if (((User) auth.getPrincipal()).getWedding() != null) {
            weddingId = ((User) auth.getPrincipal()).getWedding().getId();
        }
        
        return ResponseEntity.ok(new LoginResponseDTO(token, id, weddingId));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
        if (userRepository.findByUsername(data.username()) != null) {
            return ResponseEntity.badRequest().build();
        }
    
        // Criando um novo usuário com email
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.username(), encryptedPassword, data.role());
        newUser.setEmail(data.email());
    
        userRepository.save(newUser);
    
        return ResponseEntity.ok().build();
    }
    
}
