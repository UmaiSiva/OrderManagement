package com.assessment.OrderManagement.Controller;

import com.assessment.OrderManagement.DTO.*;
import com.assessment.OrderManagement.Entity.Client;
import com.assessment.OrderManagement.Service.ClientService;
import com.assessment.OrderManagement.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class AuthController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody Client client) {
        clientService.registerClient(client);
        return ResponseEntity.ok("Client registered successfully");
    }


    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody AuthRequest authRequest) {
        Optional<Client> client = clientService.findByEmail(authRequest.getEmail());
        if (client.isPresent() && passwordEncoder.matches(authRequest.getPassword(), client.get().getPassword())) {
            String token = jwtUtil.generateToken(authRequest.getEmail());
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

}
