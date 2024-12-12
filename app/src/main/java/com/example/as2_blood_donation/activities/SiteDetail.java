package com.example.as2_blood_donation.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as2_blood_donation.R;
import com.example.as2_blood_donation.adapters.DonorAdapter;
import com.example.as2_blood_donation.models.Donor;
import com.example.as2_blood_donation.models.enums.BloodType;

import java.util.ArrayList;

public class SiteDetail extends AppCompatActivity {
    private RecyclerView donorRecyclerView;
    private DonorAdapter donorAdapter;
    private ArrayList<Donor> donors;
    private String siteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_detail);

        siteId = getIntent().getStringExtra("siteID");
        Log.d("SiteID", siteId + "");

        ImageButton backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Initialize RecyclerView
        donorRecyclerView = findViewById(R.id.donorRecyclerView);
        donors = new ArrayList<>();

        // Fetch donor information from strings.xml
        String[] names = getResources().getStringArray(R.array.donor_names);
        String[] bloodTypes = getResources().getStringArray(R.array.donor_blood_types);
        String[] emails = getResources().getStringArray(R.array.donor_emails);

        // Populate donor list
        for (int i = 0; i < names.length; i++) {
            BloodType bloodType = BloodType.valueOf(bloodTypes[i]); // Convert string to BloodType enum
            donors.add(new Donor(String.valueOf(i), names[i], emails[i], "password123", bloodType));
        }

        donorAdapter = new DonorAdapter(this, donors);
        donorRecyclerView.setAdapter(donorAdapter);
        donorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
