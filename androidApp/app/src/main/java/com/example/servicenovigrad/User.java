package com.example.servicenovigrad;

import org.jetbrains.annotations.NotNull;

public class User {
    private final String username;
    private final String password;
    private String email;
    private String role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean equals(@NotNull User user) {
        return username.equals(user.getUsername()) && password.equals(user.getPassword());
    }

    public boolean exists(@NotNull User user) {
        return username.equals(user.getUsername());
    }

    public boolean emailEquals(String email) {
        return this.email.equals(email);
    }

    @NotNull
    public String toString() {
        return "User: " + username + " Password: " + password;
    }

}
