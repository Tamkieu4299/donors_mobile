package com.example.as2_blood_donation.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as2_blood_donation.R;
import com.example.as2_blood_donation.adapters.DonationSiteAdapter;
import com.example.as2_blood_donation.models.DonationSite;

import java.util.ArrayList;

public class ManageSites extends AppCompatActivity {
    ArrayList<DonationSite> donationSites = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sites);

        RecyclerView recyclerView = findViewById(R.id.siteRecyclerView);

        setupDonationSites();

        DonationSiteAdapter adapter = new DonationSiteAdapter(this, donationSites);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageButton backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupDonationSites() {
        // Get resources
        String[] names = getResources().getStringArray(R.array.site_names);
        String[] addresses = getResources().getStringArray(R.array.site_addresses);
        String[] latitudes = getResources().getStringArray(R.array.site_latitudes);
        String[] longitudes = getResources().getStringArray(R.array.site_longitudes);


        for (int i = 0; i < names.length; i++) {
            donationSites.add(new DonationSite(
                    String.valueOf(i),
                    names[i],
                    addresses[i],
                    Double.parseDouble(latitudes[i]),
                    Double.parseDouble(longitudes[i])
            ));
        }
    }

}