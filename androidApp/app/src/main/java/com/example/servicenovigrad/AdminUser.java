package com.example.servicenovigrad;

public class AdminUser extends User {
    public AdminUser() {
        super("admin", "admin");
        setRole("Administrator");
        setEmail("geraltOfRivia@gmail.com");
    }
}
