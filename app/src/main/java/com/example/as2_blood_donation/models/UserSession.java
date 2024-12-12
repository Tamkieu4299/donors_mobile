package com.example.as2_blood_donation.models;

import com.example.as2_blood_donation.models.enums.UserRole;

import javax.inject.Singleton;

@Singleton
public class UserSession {
    private static UserSession instance;
    private String userId;
    private String fullName;
    private String email;
    private UserRole role;

    // Private constructor to prevent instantiation
    private UserSession() {}

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    // Clear session (useful for logout or app reset)
    public void clearSession() {
        userId = null;
        fullName = null;
        email = null;
        role = null;
    }
}

