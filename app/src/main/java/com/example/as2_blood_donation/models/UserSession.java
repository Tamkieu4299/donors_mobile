package com.example.as2_blood_donation.models;

import com.example.as2_blood_donation.models.enums.UserRole;

import javax.inject.Singleton;

@Singleton
public class UserSession {
    private static UserSession instance;
    private int id;
    private String userName;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String token;
    private String tokenType;
    private int sumOfDonatedBloods;
    private String typeOfBlood;
    private UserRole role;

    // Private constructor to prevent instantiation
    private UserSession() {}

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Getters and Setters for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getSumOfDonatedBloods() {
        return sumOfDonatedBloods;
    }

    public void setSumOfDonatedBloods(int sumOfDonatedBloods) {
        this.sumOfDonatedBloods = sumOfDonatedBloods;
    }

    public String getTypeOfBlood() {
        return typeOfBlood;
    }

    public void setTypeOfBlood(String typeOfBlood) {
        this.typeOfBlood = typeOfBlood;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    // Method to populate UserSession from API response
    public void populateFromLoginResponse(UserSession session) {
        this.id = session.getId();
        this.userName = session.getUserName();
        this.firstName = session.getFirstName();
        this.lastName = session.getLastName();
        this.phone = session.getPhone();
        this.email = session.getEmail();
        this.token = session.getToken();
        this.tokenType = session.getTokenType();
        this.sumOfDonatedBloods = session.getSumOfDonatedBloods();
        this.typeOfBlood = session.getTypeOfBlood();
        this.role = session.getRole();
    }

    // Clear session (useful for logout or app reset)
    public void clearSession() {
        id = 0;
        userName = null;
        firstName = null;
        lastName = null;
        phone = null;
        email = null;
        token = null;
        tokenType = null;
        sumOfDonatedBloods = 0;
        typeOfBlood = null;
        role = null;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", sumOfDonatedBloods=" + sumOfDonatedBloods +
                ", typeOfBlood='" + typeOfBlood + '\'' +
                ", role=" + role +
                '}';
    }
}
