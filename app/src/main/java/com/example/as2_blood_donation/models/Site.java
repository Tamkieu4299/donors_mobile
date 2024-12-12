package com.example.as2_blood_donation.models;

public class Site {
    private String name;
    private String map_marker;
    private String city;
    private String street;
    private double latitude;
    private double longitude;

    public Site(String name, String city, String street, double latitude, double longitude) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters
}
