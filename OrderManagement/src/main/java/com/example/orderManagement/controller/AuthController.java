package com.example.orderManagement.controller;

import com.example.orderManagement.entity.Client;
import com.example.orderManagement.service.ClientService;
import com.example.orderManagement.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientService clientService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestBody Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientService.registerClient(client);
        return "Client registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody Client client) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(client.getEmail(), client.getPassword()));
        return jwtUtil.generateToken(client.getEmail());
    }
}
