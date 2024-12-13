package com.example.as2_blood_donation.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as2_blood_donation.R;
import com.example.as2_blood_donation.adapters.DonorAdapter;
import com.example.as2_blood_donation.api.ApiClient;
import com.example.as2_blood_donation.api.ApiService;
import com.example.as2_blood_donation.models.ApiResponse;
import com.example.as2_blood_donation.models.ApiResponseObject;
import com.example.as2_blood_donation.models.Donation;
import com.example.as2_blood_donation.models.Donor;
import com.example.as2_blood_donation.models.Site;
import com.example.as2_blood_donation.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SiteDetail extends AppCompatActivity {
    private RecyclerView donorRecyclerView;
    private DonorAdapter donorAdapter;
    private ArrayList<Donor> donors;
    private int siteId;

    private TextView siteNameTextView, siteAddressTextView, siteStatsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_detail);

        siteId = getIntent().getIntExtra("siteID", -1); // Default to -1 if siteID is missing
        if (siteId == -1) {
            Toast.makeText(this, "Site ID is missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Log.d("SiteID", "Fetching details for Site ID: " + siteId);

        ImageButton backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(view -> finish());

        // Initialize TextViews
        siteNameTextView = findViewById(R.id.siteName);
        siteAddressTextView = findViewById(R.id.siteAddress);
        siteStatsTextView = findViewById(R.id.totalBloodCollected);

        // Initialize RecyclerView
        donorRecyclerView = findViewById(R.id.donorRecyclerView);
        donors = new ArrayList<>();
        donorAdapter = new DonorAdapter(this, donors);
        donorRecyclerView.setAdapter(donorAdapter);
        donorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch site details
        fetchSiteDetails();
    }

    private void fetchSiteDetails() {
        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);

        apiService.getSiteDetails(siteId).enqueue(new Callback<ApiResponseObject<Site>>() {
            @Override
            public void onResponse(Call<ApiResponseObject<Site>> call, Response<ApiResponseObject<Site>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponseObject<Site> apiResponse = response.body();
                    if ("success".equals(apiResponse.getStatus()) && apiResponse.getData() != null) {
                        Site site = apiResponse.getData();
                        Log.d("API Response", "Site: " + site.getName() + ", Donations: " + site.getDonations());
                        populateSiteDetails(site);
                    } else {
                        Toast.makeText(SiteDetail.this, "Failed to fetch site details", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SiteDetail.this, "Response unsuccessful or body null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponseObject<Site>> call, Throwable t) {
                Toast.makeText(SiteDetail.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateSiteDetails(Site site) {
        // Populate site details in UI
        siteNameTextView.setText(site.getName());
        siteAddressTextView.setText(site.getStreet() + ", " + site.getCity());
        siteStatsTextView.setText(
                "Donors: " + site.getAmountOfDonors() +
                        "\nApproved Donors: " + site.getAmountOfApprovedDonors() +
                        "\nBlood Amount: " + site.getAmountOfBlood() + " units"
        );

        // Handle donations
        if (site.getDonations() != null && !site.getDonations().isEmpty()) {
            donors.clear();
            Log.d("SiteDetail", "Total donors fetched: " + site.getDonations().size());
            for (Donor donor : site.getDonations()) {
                if (donor != null) {
                    Log.d("Donor Details", "Name: " + donor.getName() + ", Email: " + donor.getEmail() + ", blood: " + donor.getBloodType());
                    donors.add(donor);
                } else {
                    Log.w("SiteDetail", "Donor is null");
                }
            }
            donorAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No donations available for this site", Toast.LENGTH_SHORT).show();
        }
    }
}
