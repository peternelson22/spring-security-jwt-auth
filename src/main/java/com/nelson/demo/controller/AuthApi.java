package com.nelson.demo.controller;

import com.nelson.demo.config.JwtUtil;
import com.nelson.demo.model.UserEntity;
import com.nelson.demo.payload.AuthRequest;
import com.nelson.demo.payload.AuthResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class AuthApi {

    private AuthenticationManager authenticationManager;

    private JwtUtil util;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request){

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()));

            UserEntity user = (UserEntity) authentication.getPrincipal();
            String token = util.generateToken(user);
            AuthResponse response = new AuthResponse(user.getEmail(), token);

            return ResponseEntity.ok().body(response);
        }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
