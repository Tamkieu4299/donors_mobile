package com.example.as2_blood_donation.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.as2_blood_donation.MainActivity;
import com.example.as2_blood_donation.R;
import com.example.as2_blood_donation.api.ApiClient;
import com.example.as2_blood_donation.api.ApiService;
import com.example.as2_blood_donation.models.ApiResponse;
import com.example.as2_blood_donation.models.DonationSite;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private ApiService apiService;
    private List<DonationSite> donationSites = new ArrayList<>();
    private EditText searchSiteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Back Button to return to MainActivity
        ImageButton buttonBackToMain = findViewById(R.id.buttonBackToMain);
        buttonBackToMain.setOnClickListener(view -> {
            Intent intent = new Intent(MapsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Search Site EditText
        searchSiteEditText = findViewById(R.id.searchSiteEditText);
        searchSiteEditText.setOnEditorActionListener((v, actionId, event) -> {
            searchSiteByName();
            return true; // Consume the event
        });

        // Search Button
        ImageButton searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(view -> searchSiteByName());

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize API service
        apiService = ApiClient.getApiClient().create(ApiService.class);

        // Set up the Google Map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Enable location features if permissions are granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            getCurrentLocation();
        } else {
            // Request location permissions if not already granted
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1);
        }

        // Fetch and add donation site markers
        fetchDonationSites();
    }

    private void fetchDonationSites() {
        apiService.getDonationSites().enqueue(new Callback<ApiResponse<DonationSite>>() {
            @Override
            public void onResponse(Call<ApiResponse<DonationSite>> call, Response<ApiResponse<DonationSite>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    donationSites = response.body().getData();
                    if (donationSites != null && !donationSites.isEmpty()) {
                        addMarkersForDonationSites();
                    } else {
                        Toast.makeText(MapsActivity.this, "No donation sites to display.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MapsActivity.this, "Failed to fetch donation sites.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<DonationSite>> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addMarkersForDonationSites() {
        for (DonationSite site : donationSites) {
            try {
                LatLng location = new LatLng(site.getLatitude(), site.getLongtitude());

                Log.d("MapsActivity", "Site " + site.getName() + " : " + site.getLatitude() + ", Longitude: " + site.getLongtitude());
                mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(site.getName()));
            } catch (Exception e) {
                Toast.makeText(this, "Error adding marker for: " + site.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                // Print latitude and longitude
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Toast.makeText(this, "Latitude: " + latitude + ", Longitude: " + longitude, Toast.LENGTH_SHORT).show();
                Log.d("MapsActivity", "Current Location - Latitude: " + latitude + ", Longitude: " + longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
            } else {
                Toast.makeText(this, "Unable to fetch current location", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void searchSiteByName() {
        String searchQuery = searchSiteEditText.getText().toString().trim();
        if (searchQuery.isEmpty()) {
            Toast.makeText(this, "Enter a site name to search", Toast.LENGTH_SHORT).show();
            return;
        }

        for (DonationSite site : donationSites) {
            if (site.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                LatLng siteLatLng = new LatLng(site.getLatitude(), site.getLongtitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(siteLatLng, 15));
                Toast.makeText(this, "Found: " + site.getName(), Toast.LENGTH_SHORT).show();
                Log.d("MapsActivity", "Site " + site.getName() + " : " + site.getLatitude() + ", Longitude: " + site.getLongtitude());
                return;
            }
        }
        Toast.makeText(this, "No site found with name: " + searchQuery, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    getCurrentLocation();
                }
            } else {
                Toast.makeText(this, "Location permissions are required to use this feature.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
