package com.example.ordermanagement.controller;

import com.example.ordermanagement.entity.Client;
import com.example.ordermanagement.repository.ClientRepository;
import com.example.ordermanagement.utils.jwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class ClientController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private jwtUtil jwtUtil;

    /**
     * Endpoint for registering a new user.
     * @param client - Client object with user details.
     * @return Success message after registration.
     */
    @PostMapping("/signup")
    public String signup(@RequestBody Client client) {
        // Encode the user's password before saving
        client.setPassword(passwordEncoder.encode(client.getPassword()));

        // Save the new client in the database
        clientRepository.save(client);

        // Inform the user of successful registration
        return "User registered successfully!";
    }

    /**
     * Endpoint for user login, returns JWT token if successful.
     * @param client - Client object with email and password for login.
     * @return JWT token upon successful authentication.
     * @throws Exception if authentication fails.
     */
    @PostMapping("/login")
    public String login(@RequestBody Client client) throws Exception {
        try {
            // Authenticate using the provided email and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(client.getEmail(), client.getPassword())
            );
        } catch (AuthenticationException e) {
            // Throw exception if authentication fails
            throw new Exception("Invalid credentials");
        }

        // Generate and return JWT token if authentication is successful
        return jwtUtil.generateToken(client.getEmail());
    }
}
