package com.example.as2_blood_donation.models;

import android.util.Log;

import com.example.as2_blood_donation.models.enums.UserRole;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SuperUser extends User {
    public SuperUser(String name, String email, String password, UserRole role) {
        super(name, email, password, role);
    }
    public static void saveSuperUser(SuperUser superUser) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", superUser.getId());
        userMap.put("name", superUser.getName());
        userMap.put("email", superUser.getEmail());

        db.collection("superUsers").document(superUser.getId())
                .set(userMap)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "SuperUser saved successfully"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error saving SuperUser", e));
    }
}
