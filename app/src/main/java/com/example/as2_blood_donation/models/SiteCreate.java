package com.example.as2_blood_donation.models;

public class SiteCreate {
    private String name;
    private String city;
    private String street;
    private double latitude;
    private double longtitude;

    public SiteCreate(String name, String city, String street, double latitude, double longtitude) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    // Getters
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getStreet() { return street; }
    public double getLatitude() { return latitude; }
    public double getLongtitude() { return longtitude; }
}
