package com.example.as2_blood_donation.models;

import android.util.Log;

import com.example.as2_blood_donation.models.enums.BloodType;
import com.example.as2_blood_donation.models.enums.UserRole;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Donor extends User {

    public Donor(String id, String firstName, String lastName, String email, String password, String type_of_blood) {
        super(id, firstName, lastName, email, password, UserRole.DONOR, type_of_blood);
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
