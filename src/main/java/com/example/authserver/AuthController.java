package com.example.authserver;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class AuthController {

    private Map<String, String> passwordStore = new HashMap<>();
    private Set<String> authenticatedUsers = new HashSet<>();
    private Random rand = new Random();

    @GetMapping("/request/{name}")
    public String requestPassword(@PathVariable String name) {

        String password = String.valueOf(1000 + rand.nextInt(9000));

        passwordStore.put(name, password);

        return password;
    }

    @GetMapping("/auth/{name}/{password}")
    public String authenticate(
            @PathVariable String name,
            @PathVariable String password) {

        String stored = passwordStore.get(name);

        if (stored != null && stored.equals(password)) {
            authenticatedUsers.add(name);
            return "Authentication successful for " + name;
        }

        return "Authentication failed";
    }

    @GetMapping("/auth")
    public Set<String> getAuthenticatedUsers() {
        return authenticatedUsers;
    }
}