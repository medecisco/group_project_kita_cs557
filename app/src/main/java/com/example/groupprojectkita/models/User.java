package com.example.groupprojectkita.models; // Create this 'models' subpackage

public class User {
    private String email;
    private String password;
    private String name;

    public User() {}

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; } // Remember to hash in real app

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}