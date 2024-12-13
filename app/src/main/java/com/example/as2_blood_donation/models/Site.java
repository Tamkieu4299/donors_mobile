package com.example.as2_blood_donation.models;

public class Site {
    private int id;
    private String name;
    private String city;
    private String street;
    private double latitude;
    private double longtitude;
    private int amount_of_donors;
    private int amount_of_approved_donors;
    private int amount_of_blood;

    public Site(int id, String name, String city, String street, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.latitude = latitude;
        this.longtitude = longitude;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getStreet() { return street; }
    public double getLatitude() { return latitude; }
    public double getLongtitude() { return longtitude; }
    public int getAmountOfDonors() { return amount_of_donors; }
    public int getAmountOfApprovedDonors() { return amount_of_approved_donors; }
    public int getAmountOfBlood() { return amount_of_blood; }
}
