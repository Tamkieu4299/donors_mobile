package com.example.as2_blood_donation.models;

import java.util.ArrayList;
import java.util.List;

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

    private List<Donor> list_of_donors = new ArrayList<>();

    public Site(int id, String name, String city, String street, double latitude, double longitude, List<Donor> list_of_donors) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.latitude = latitude;
        this.longtitude = longitude;
        this.list_of_donors = list_of_donors;
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
    public List<Donor> getDonations() { return list_of_donors; } // Add getter
}
