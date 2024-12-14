package com.example.as2_blood_donation.activities;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import com.example.as2_blood_donation.models.UserSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // Volunteer Button functionality
        Button volunteerButton = findViewById(R.id.volunteerButton);
        volunteerButton.setOnClickListener(v -> registerAsVolunteer());
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

    // Method to register as a volunteer
    private void registerAsVolunteer() {
        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);

        // Get user_id from UserSession
        int userId = UserSession.getInstance().getId();
        if (userId <= 0 || siteId <= 0) {
            Toast.makeText(this, "Invalid User or Site ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare the request body
        Map<String, Integer> requestBody = new HashMap<>();
        requestBody.put("user_id", userId);
        requestBody.put("site_id", siteId);

        // Make the API call
        apiService.registerVolunteer(requestBody).enqueue(new Callback<ApiResponseObject<Void>>() {
            @Override
            public void onResponse(Call<ApiResponseObject<Void>> call, Response<ApiResponseObject<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponseObject<Void> apiResponse = response.body();
                    if ("success".equals(apiResponse.getStatus())) {
                        Toast.makeText(SiteDetail.this, "Successfully registered as a volunteer!", Toast.LENGTH_SHORT).show();
                        fetchSiteDetails();
                    } else {
                        Toast.makeText(SiteDetail.this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SiteDetail.this, "Failed to register. Server error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponseObject<Void>> call, Throwable t) {
                Toast.makeText(SiteDetail.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Volunteer API Error", t.getMessage(), t);
            }
        });
    }
    public void showApproveDonorDialog(int donorId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Approve Donor");

        // Create an input field for volume of blood
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint("Enter volume of blood (ml)");
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Approve", (dialog, which) -> {
            String volumeInput = input.getText().toString();
            if (!TextUtils.isEmpty(volumeInput)) {
                int volumeOfBlood = Integer.parseInt(volumeInput);
                approveDonor(siteId, donorId, volumeOfBlood);
            } else {
                Toast.makeText(SiteDetail.this, "Volume of blood is required", Toast.LENGTH_SHORT).show();
                showApproveDonorDialog(donorId); // Reopen dialog for input
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void approveDonor(int siteId, int donorId, int volumeOfBlood) {
        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        apiService.approveDonation(siteId, donorId, volumeOfBlood).enqueue(new Callback<ApiResponseObject<Void>>() {
            @Override
            public void onResponse(Call<ApiResponseObject<Void>> call, Response<ApiResponseObject<Void>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponseObject<Void> apiResponse = response.body();
                    if ("success".equals(apiResponse.getStatus())) {
                        Toast.makeText(SiteDetail.this, "Donor approved successfully!", Toast.LENGTH_SHORT).show();
                        fetchSiteDetails(); // Refresh the donor list to show updated state
                    } else {
                        Toast.makeText(SiteDetail.this, "Failed to approve donor. Try again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SiteDetail.this, "Failed to approve donor. Server error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponseObject<Void>> call, Throwable t) {
                Toast.makeText(SiteDetail.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Approve Donor API Error", t.getMessage(), t);
            }
        });
    }

}
