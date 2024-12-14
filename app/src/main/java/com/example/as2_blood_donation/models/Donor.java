package com.example.as2_blood_donation.models;

import android.util.Log;

import com.example.as2_blood_donation.models.enums.UserRole;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Donor extends User {
    @SerializedName("has_approved")
    private boolean has_approved;

    // Constructor
    public Donor(String id, String firstName, String lastName, String email, String password, String type_of_blood, boolean hasApproved) {
        super(id, firstName, lastName, email, password, UserRole.DONOR, type_of_blood);
        this.has_approved = hasApproved;
    }

    // Getter and Setter for hasApproved
    public boolean isApproved() {
        return has_approved;
    }

    public void setHasApproved(boolean hasApproved) {
        this.has_approved = hasApproved;
    }

    // Save Donor to Firestore (Optional Firebase Usage)
    public static void saveDonor(Donor donor) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", donor.getId());
        userMap.put("name", donor.getName());
        userMap.put("email", donor.getEmail());
        userMap.put("bloodType", donor.getBloodType());
        userMap.put("hasApproved", donor.isApproved());

        db.collection("donors").document(donor.getId())
                .set(userMap)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Donor saved successfully"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error saving donor", e));
    }
}
