package org.example.controllers;

import org.example.models.entities.User;
import org.example.models.repositories.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthController {
    private UserRepository userRepository = UserRepository.getInstance();

    public boolean register(String username, String password, String role) {
        if (userRepository.findByUsername(username) != null) return false;
        String hashed = hashPassword(password);
        userRepository.create(new User(0, username, hashed, role));
        return true;
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) return null;
        String hashed = hashPassword(password);
        return user.getHashedPassword().equals(hashed) ? user : null;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) hex.append(String.format("%02x", b));
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
