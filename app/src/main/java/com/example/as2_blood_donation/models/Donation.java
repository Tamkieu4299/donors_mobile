package com.example.as2_blood_donation.models;

import java.util.Date;

public class Donation {
    private int id;
    private User user; // A User object to represent the user field
    private Site site; // A Site object to represent the site field
    private Date created_at; // Use java.util.Date or any appropriate type

    // Getters and setters
    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Site getSite() {
        return site;
    }

    public Date getCreatedAt() {
        return created_at;
    }
}
