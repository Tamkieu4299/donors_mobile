package com.example.as2_blood_donation.models;

import android.util.Log;

import com.example.as2_blood_donation.models.enums.BloodType;
import com.example.as2_blood_donation.models.enums.UserRole;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Donor extends User {
    private BloodType bloodType;

    public Donor(String id, String name, String email, String password, BloodType bloodType) {
        super(id, name, email, password, UserRole.DONOR);
        this.bloodType = bloodType;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public static void saveDonor(Donor donor) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", donor.getId());
        userMap.put("name", donor.getName());
        userMap.put("email", donor.getEmail());
        userMap.put("bloodType", ((Donor) donor).getBloodType().toString());


        db.collection("donors").document(donor.getId())
                .set(userMap)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Donor saved successfully"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error saving donor", e));
    }
}
