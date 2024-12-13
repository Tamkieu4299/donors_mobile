package com.example.as2_blood_donation.models;

import android.util.Log;

import com.example.as2_blood_donation.models.enums.UserRole;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SiteManager extends User {
    private Set<String> managingSites;

    public SiteManager(String firstName, String lastName,  String email, String password, UserRole role, Set<String> managingSites) {
        super(firstName, lastName, email, password, role);
        this.managingSites = managingSites;
    }

    public Set<String> getManagingSites() {
        return managingSites;
    }

    public void setManagingSites(Set<String> managingSites) {
        this.managingSites = managingSites;
    }

    public static void saveSiteManager(SiteManager siteManager) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", siteManager.getId());
        userMap.put("name", siteManager.getName());
        userMap.put("email", siteManager.getEmail());
        userMap.put("managingSites", siteManager.getManagingSites());

        db.collection("siteManagers").document(siteManager.getId())
                .set(userMap)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "SiteManager saved successfully"))
                .addOnFailureListener(e -> Log.e("Firestore", "Error saving SiteManager", e));
    }
}
