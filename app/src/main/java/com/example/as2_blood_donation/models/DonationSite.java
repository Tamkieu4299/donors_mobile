//package com.example.as2_blood_donation.models;
//
//
//import com.example.as2_blood_donation.models.enums.BloodType;
//
//import java.util.Date;
//import java.util.Set;
//
//public class DonationSite {
//    private String id;
//    private String name;
//    private String address;
//    private double latitude;
//    private double longitude;
//    private Date date;
//    private Set<BloodType> requiredBloodTypes;
//    private int totalDonors;
//    private double totalBloodCollected;
//
//    public DonationSite(String id, String name, String address, double latitude, double longitude) {
//        this.id = id;
//        this.name = name;
//        this.address = address;
//        this.latitude = latitude;
//        this.longitude = longitude;
//    }
//
//    public DonationSite(String id, String name, String address, double latitude, double longitude, Date date, Set<BloodType> requiredBloodTypes) {
//        this.id = id;
//        this.name = name;
//        this.address = address;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.date = date;
//        this.requiredBloodTypes = requiredBloodTypes;
//    }
//
//    public DonationSite(String id, String name, String address, double latitude, double longitude, Date date, Set<BloodType> requiredBloodTypes, int totalDonors, double totalBloodCollected) {
//        this.id = id;
//        this.name = name;
//        this.address = address;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.date = date;
//        this.requiredBloodTypes = requiredBloodTypes;
//        this.totalDonors = totalDonors;
//        this.totalBloodCollected = totalBloodCollected;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }
//
//    public double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public Set<BloodType> getRequiredBloodTypes() {
//        return requiredBloodTypes;
//    }
//
//    public void setRequiredBloodTypes(Set<BloodType> requiredBloodTypes) {
//        this.requiredBloodTypes = requiredBloodTypes;
//    }
//
//    public int getTotalDonors() {
//        return totalDonors;
//    }
//
//    public void setTotalDonors(int totalDonors) {
//        this.totalDonors = totalDonors;
//    }
//
//    public double getTotalBloodCollected() {
//        return totalBloodCollected;
//    }
//
//    public void setTotalBloodCollected(double totalBloodCollected) {
//        this.totalBloodCollected = totalBloodCollected;
//    }
//}

package com.example.as2_blood_donation.models;

public class DonationSite {
    private int id;
    private String name;
    private String city;
    private String street;
    private double latitude;
    private double longtitude;
    private int amount_of_donors;
    private int amount_of_approved_donors;
    private int amount_of_blood;

    // Constructors, Getters, and Setters
    public DonationSite(int id, String name, String city, String street, double latitude, double longtitude) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getStreet() { return street; }
    public double getLatitude() { return latitude; }
    public double getLongtitude() { return longtitude; }
    public int getAmount_of_donors() { return amount_of_donors; }
    public int getAmount_of_approved_donors() { return amount_of_approved_donors; }
    public int getAmount_of_blood() { return amount_of_blood; }
}
