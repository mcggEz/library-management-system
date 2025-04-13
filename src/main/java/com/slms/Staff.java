package com.slms;

public class Staff {

    private String username;
    private String password;

    // Constructor
    public Staff(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }
}
