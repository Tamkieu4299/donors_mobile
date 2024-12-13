package com.example.as2_blood_donation.models;

import android.util.Log;

import com.example.as2_blood_donation.models.enums.UserRole;
import com.example.as2_blood_donation.utils.IDUtils;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public abstract class User {
    private String id;
    private String email;
    private String password;
    private UserRole role;
    private String first_name;
    private String last_name;
    private String name = "";
    private String type_of_blood = "Unknown";

    public User(String id, String firstName, String lastName, String email, String password, UserRole role, String type_of_blood) {
        this.id = id;
        this.name = firstName + " " + lastName;
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.type_of_blood = type_of_blood;
    }

    public User(String firstName, String lastName, String email, String password, UserRole role) {
        this.id = IDUtils.generateRandomId();
        this.name = firstName + " " + lastName;
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }
    public String getBloodType() {
        return type_of_blood;
    }

    public void setName(String name) {
        this.name = name;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void saveUser(User user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("name", user.getName());
        userMap.put("email", user.getEmail());
        userMap.put("role", user.getRole().toString());

        if (user instanceof Donor) {
            userMap.put("bloodType", ((Donor) user).getBloodType().toString());
        } else if (user instanceof SiteManager) {
            userMap.put("managingSites", ((SiteManager) user).getManagingSites());
        }

        db.collection("users").document(user.getId())
                .set(userMap)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "User saved successfully"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error saving user", e));
    }

}
