package com.example.as2_blood_donation.models;

public class RegisterRequest {
    private String user_name;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private int gender;
    private String phone;
    private int sum_of_do_bloods;
    private String type_of_blood;
    private String role;

    // Constructor
    public RegisterRequest(String user_name, String first_name, String last_name, String email, String password,
                           int gender, String phone, int sum_of_do_bloods, String type_of_blood, String role) {
        this.user_name = user_name;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.sum_of_do_bloods = sum_of_do_bloods;
        this.type_of_blood = type_of_blood;
        this.role = role;
    }

    // Default constructor
    public RegisterRequest() {
    }

    // Getters and Setters
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSum_of_do_bloods() {
        return sum_of_do_bloods;
    }

    public void setSum_of_do_bloods(int sum_of_do_bloods) {
        this.sum_of_do_bloods = sum_of_do_bloods;
    }

    public String getType_of_blood() {
        return type_of_blood;
    }

    public void setType_of_blood(String type_of_blood) {
        this.type_of_blood = type_of_blood;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

