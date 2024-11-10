package com.example.orderManagement.controller;

import com.example.orderManagement.entity.Client;
import com.example.orderManagement.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // Register a new client
    @PostMapping("/register")
    public Client registerClient(@RequestBody Client client) {
        return clientService.registerClient(client);
    }

    // Retrieve a client by email
    @GetMapping("/email/{email}")
    public Client getClientByEmail(@PathVariable String email) {
        Optional<Client> clientOptional = clientService.findClientByEmail(email);
        return clientOptional.orElse(null); // Returns null if client is not found
    }
}
