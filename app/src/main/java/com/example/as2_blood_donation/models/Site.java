package com.example.as2_blood_donation.models;

public class Site {
    private String name;
    private String map_marker;
    private String city;
    private String street;
    private double latitude;
    private double longitude;

    public Site(String name, String map_marker, String city, String street, double latitude, double longitude) {
        this.name = name;
        this.map_marker = map_marker;
        this.city = city;
        this.street = street;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters
}
