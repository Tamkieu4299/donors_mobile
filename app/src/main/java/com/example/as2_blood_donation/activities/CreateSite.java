package com.example.as2_blood_donation.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.as2_blood_donation.R;
import com.example.as2_blood_donation.api.ApiClient;
import com.example.as2_blood_donation.api.ApiService;
import com.example.as2_blood_donation.models.Site;
import com.example.as2_blood_donation.models.SiteCreate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateSite extends AppCompatActivity {

    private EditText inputSiteName, inputSiteCity,inputSiteStreet, inputLatitude, inputLongitude;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_site);

        // Initialize API Service
        apiService = ApiClient.getApiClient().create(ApiService.class);

        inputSiteName = findViewById(R.id.inputSiteName);
        inputSiteCity= findViewById(R.id.inputSiteCity);
        inputSiteStreet= findViewById(R.id.inputSiteStreet);
        inputLatitude = findViewById(R.id.inputLatitude);
        inputLongitude = findViewById(R.id.inputLongitude);

        Button submitButton = findViewById(R.id.submitSiteButton);
        ImageButton backButton = findViewById(R.id.buttonBack);

        // Handle Back Button
        backButton.setOnClickListener(view -> finish());

        // Handle Submit Button
        submitButton.setOnClickListener(view -> handleSubmit());
    }

    private void handleSubmit() {
        String siteName = inputSiteName.getText().toString().trim();
        String siteStreet = inputSiteStreet.getText().toString().trim();
        String siteCity = inputSiteCity.getText().toString().trim();
        String latitudeStr = inputLatitude.getText().toString().trim();
        String longitudeStr = inputLongitude.getText().toString().trim();

        if (siteName.isEmpty() || siteStreet.isEmpty() || siteCity.isEmpty() || latitudeStr.isEmpty() || longitudeStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double latitude, longitude;
        try {
            latitude = Double.parseDouble(latitudeStr);
            longitude = Double.parseDouble(longitudeStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid latitude or longitude format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create Site object
        SiteCreate newSite = new SiteCreate(
                siteName,
                siteCity,
                siteStreet,
                latitude,
                longitude
        );

        // Make POST request to API
        apiService.createSite(newSite).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateSite.this, "Site successfully created!", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                } else {
                    Toast.makeText(CreateSite.this, "Failed to create site. Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(CreateSite.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", t.getMessage(), t);
            }
        });
    }
}
