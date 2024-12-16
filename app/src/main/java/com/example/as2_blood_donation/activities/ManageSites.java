package com.example.as2_blood_donation.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.as2_blood_donation.R;
import com.example.as2_blood_donation.adapters.DonationSiteAdapter;
import com.example.as2_blood_donation.api.ApiClient;
import com.example.as2_blood_donation.api.ApiService;
import com.example.as2_blood_donation.models.DonationSite;
import com.example.as2_blood_donation.models.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageSites extends AppCompatActivity {
    private ArrayList<DonationSite> donationSites = new ArrayList<>();
    private DonationSiteAdapter adapter;

    // Filter UI components
    private EditText filterName, filterCity, filterStreet;
    private EditText filterAmountOfDonors, filterAmountOfApprovedDonors, filterAmountOfBlood;
    private Button filterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sites);

        RecyclerView recyclerView = findViewById(R.id.siteRecyclerView);

        // Initialize RecyclerView and Adapter
        adapter = new DonationSiteAdapter(this, donationSites);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize UI components
        filterName = findViewById(R.id.filterName);
        filterCity = findViewById(R.id.filterCity);
        filterStreet = findViewById(R.id.filterStreet);
        filterAmountOfDonors = findViewById(R.id.filterAmountOfDonors);
        filterAmountOfApprovedDonors = findViewById(R.id.filterAmountOfApprovedDonors);
        filterAmountOfBlood = findViewById(R.id.filterAmountOfBlood);
        filterButton = findViewById(R.id.filterButton);


        // Set up back button
        ImageButton backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(view -> finish());

        // Fetch initial data
        fetchDonationSites();

        // Apply filter logic
        filterButton.setOnClickListener(view -> applyFilter());
    }

    private void fetchDonationSites() {
        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);

        apiService.getDonationSites().enqueue(new Callback<ApiResponse<DonationSite>>() {
            @Override
            public void onResponse(Call<ApiResponse<DonationSite>> call, Response<ApiResponse<DonationSite>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    donationSites.clear();
                    donationSites.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ManageSites.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<DonationSite>> call, Throwable t) {
                Toast.makeText(ManageSites.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void applyFilter() {
        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);

        // Prepare request body
        Map<String, Object> filterMap = new HashMap<>();

        if (!filterName.getText().toString().trim().isEmpty()) {
            filterMap.put("name", filterName.getText().toString().trim());
        }
        if (!filterCity.getText().toString().trim().isEmpty()) {
            filterMap.put("city", filterCity.getText().toString().trim());
        }
        if (!filterStreet.getText().toString().trim().isEmpty()) {
            filterMap.put("street", filterStreet.getText().toString().trim());
        }
        // For numeric inputs (e.g., amount_of_donors)
        if (!filterAmountOfDonors.getText().toString().trim().isEmpty()) {
            int amountOfDonors = Integer.parseInt(filterAmountOfDonors.getText().toString().trim());
            filterMap.put("amount_of_donors", amountOfDonors);
        }
        if (!filterAmountOfApprovedDonors.getText().toString().trim().isEmpty()) {
            int approvedDonors = Integer.parseInt(filterAmountOfApprovedDonors.getText().toString().trim());
            filterMap.put("amount_of_approved_donors", approvedDonors);
        }
        if (!filterAmountOfBlood.getText().toString().trim().isEmpty()) {
            int amountOfBlood = Integer.parseInt(filterAmountOfBlood.getText().toString().trim());
            filterMap.put("amount_of_blood", amountOfBlood);
        }

        // Check if the map is empty
        if (filterMap.isEmpty()) {
            Toast.makeText(ManageSites.this, "Please enter at least one filter.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Make the API call
        apiService.filterDonationSites(0, 10, filterMap).enqueue(new Callback<ApiResponse<DonationSite>>() {
            @Override
            public void onResponse(Call<ApiResponse<DonationSite>> call, Response<ApiResponse<DonationSite>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    donationSites.clear();
                    donationSites.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ManageSites.this, "No sites match the filter.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<DonationSite>> call, Throwable t) {
                Toast.makeText(ManageSites.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
