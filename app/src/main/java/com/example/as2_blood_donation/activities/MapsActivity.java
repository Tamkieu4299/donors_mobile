package com.example.as2_blood_donation.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.as2_blood_donation.MainActivity;
import com.example.as2_blood_donation.R;
import com.example.as2_blood_donation.api.ApiClient;
import com.example.as2_blood_donation.api.ApiService;
import com.example.as2_blood_donation.models.ApiResponse;
import com.example.as2_blood_donation.models.DonationSite;
import com.example.as2_blood_donation.models.RouteResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.as2_blood_donation.models.ApiResponseObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private ApiService apiService;
    private List<DonationSite> donationSites = new ArrayList<>();
    private EditText searchSiteEditText;
    private Map<Marker, DonationSite> markerSiteMap = new HashMap<>();

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

        // Button to find nearest site
        Button findNearestSiteButton = findViewById(R.id.findNearestSiteButton);
        findNearestSiteButton.setOnClickListener(view -> findNearestSite());

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

    @SuppressLint("PotentialBehaviorOverride")
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

        // Set a marker click listener
        mMap.setOnMarkerClickListener(marker -> {
            DonationSite site = markerSiteMap.get(marker); // Retrieve the site associated with the marker
            if (site != null) {
                showMarkerOptionsDialog(site); // Pass the site ID to SiteDetail
                return true; // Return true to indicate the click has been handled
            }
            return false;
        });
    }
    private void showMarkerOptionsDialog(DonationSite site) {
        new AlertDialog.Builder(this)
                .setTitle(site.getName())
                .setPositiveButton("View Details", (dialog, which) -> navigateToSiteDetail(site.getId()))
                .setNegativeButton("Find Path", (dialog, which) -> findRouteToSite(site))
                .setNeutralButton("Cancel", null)
                .show();
    }

    private void findRouteToSite(DonationSite site) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permissions are required to find routes", Toast.LENGTH_SHORT).show();
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                double userLat = location.getLatitude();
                double userLng = location.getLongitude();

                // Call the API to get the route
                fetchRouteFromApi(userLat, userLng, site.getLatitude(), site.getLongtitude());
            } else {
                Toast.makeText(this, "Unable to fetch current location", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error fetching location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void fetchRouteFromApi(double userLat, double userLng, double destinationLat, double destinationLng) {
        Map<String, Double> requestBody = new HashMap<>();
        requestBody.put("user_lat", userLat);
        requestBody.put("user_lng", userLng);
        requestBody.put("destination_lat", destinationLat);
        requestBody.put("destination_lng", destinationLng);

        apiService.getRoute(requestBody).enqueue(new Callback<RouteResponse>() {
            @Override
            public void onResponse(Call<RouteResponse> call, Response<RouteResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    drawRoute(response.body().getOverviewPolyline());
                } else {
                    Toast.makeText(MapsActivity.this, "Failed to fetch route", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RouteResponse> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void drawRoute(String overviewPolyline) {
        List<LatLng> routePoints = PolyUtil.decode(overviewPolyline);

        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(routePoints)
                .width(10)
                .color(Color.BLUE)
                .geodesic(true);

        mMap.addPolyline(polylineOptions);

        // Move camera to start of the route
        if (!routePoints.isEmpty()) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(routePoints.get(0), 15));
        }
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
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(site.getName()));
                markerSiteMap.put(marker, site);
            } catch (Exception e) {
                Toast.makeText(this, "Error adding marker for: " + site.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToSiteDetail(int siteId) {
        Intent intent = new Intent(MapsActivity.this, SiteDetail.class);
        intent.putExtra("siteID", siteId);
        startActivity(intent);
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

    private void findNearestSite() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permissions are required to use this feature.", Toast.LENGTH_SHORT).show();
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                double userLat = location.getLatitude();
                double userLng = location.getLongitude();

                // Call API to fetch nearest site
                apiService.getNearestSite(userLat, userLng).enqueue(new Callback<ApiResponseObject<DonationSite>>() {
                    @Override
                    public void onResponse(Call<ApiResponseObject<DonationSite>> call, Response<ApiResponseObject<DonationSite>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            DonationSite nearestSite = response.body().getData();
                            if (nearestSite != null) {
                                LatLng nearestSiteLatLng = new LatLng(nearestSite.getLatitude(), nearestSite.getLongtitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nearestSiteLatLng, 15));
                                Toast.makeText(MapsActivity.this, "Nearest Site: " + nearestSite.getName(), Toast.LENGTH_SHORT).show();
                                Log.d("MapsActivity", "Site " + nearestSite.getName() + " : " + nearestSite.getLatitude() + ", Longitude: " + nearestSite.getLongtitude());
                            } else {
                                Toast.makeText(MapsActivity.this, "No nearest site found.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MapsActivity.this, "Failed to fetch nearest site.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponseObject<DonationSite>> call, Throwable t) {
                        Toast.makeText(MapsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Unable to fetch current location", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
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
