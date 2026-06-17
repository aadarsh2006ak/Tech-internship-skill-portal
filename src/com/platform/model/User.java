package com.platform.model;

import com.platform.service.JobPortalSystem;
import java.util.Scanner;

/**
 * Demonstrates INHERITANCE and POLYMORPHISM.
 * This is the general base class handling login credentials.
 * It defines the polymorphic login() method that subclasses must override.
 */
public abstract class User {
    protected String username;
    protected String password;
    protected String fullName;

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    /**
     * Authenticates the user with the entered password.
     */
    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    /**
     * Polymorphic method: returns a description of the user's role.
     */
    public abstract String getRoleDescription();

    /**
     * Polymorphic method: displays options and behaves differently
     * based on whether the runtime instance is a StudentUser or RecruiterUser.
     */
    public abstract void login(Scanner scanner, JobPortalSystem portal);
}
