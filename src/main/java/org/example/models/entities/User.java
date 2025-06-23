package org.example.models.entities;

public class User {
    private int id;
    private String username;
    private String hashedPassword;
    private String role; // Например: "admin", "user"

    public User(int id, String username, String hashedPassword, String role) {
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getHashedPassword() { return hashedPassword; }
    public String getRole() { return role; }

    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }
    public void setRole(String role) { this.role = role; }



}
