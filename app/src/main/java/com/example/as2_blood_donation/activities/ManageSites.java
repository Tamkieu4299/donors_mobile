//package com.example.as2_blood_donation.activities;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageButton;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.as2_blood_donation.R;
//import com.example.as2_blood_donation.adapters.DonationSiteAdapter;
//import com.example.as2_blood_donation.models.DonationSite;
//
//import java.util.ArrayList;
//
//public class ManageSites extends AppCompatActivity {
//    ArrayList<DonationSite> donationSites = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_manage_sites);
//
//        RecyclerView recyclerView = findViewById(R.id.siteRecyclerView);
//
//        setupDonationSites();
//
//        DonationSiteAdapter adapter = new DonationSiteAdapter(this, donationSites);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        ImageButton backButton = findViewById(R.id.buttonBack);
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//    }
//
//    private void setupDonationSites() {
//        // Get resources
//        String[] names = getResources().getStringArray(R.array.site_names);
//        String[] addresses = getResources().getStringArray(R.array.site_addresses);
//        String[] latitudes = getResources().getStringArray(R.array.site_latitudes);
//        String[] longitudes = getResources().getStringArray(R.array.site_longitudes);
//
//
//        for (int i = 0; i < names.length; i++) {
//            donationSites.add(new DonationSite(
//                    String.valueOf(i),
//                    names[i],
//                    addresses[i],
//                    Double.parseDouble(latitudes[i]),
//                    Double.parseDouble(longitudes[i])
//            ));
//        }
//    }
//
//}

package com.example.as2_blood_donation.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageSites extends AppCompatActivity {
    ArrayList<DonationSite> donationSites = new ArrayList<>();
    DonationSiteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_sites);

        RecyclerView recyclerView = findViewById(R.id.siteRecyclerView);

        adapter = new DonationSiteAdapter(this, donationSites);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageButton backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(view -> finish());

        fetchDonationSites();
    }

    private void fetchDonationSites() {
        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);

        apiService.getDonationSites().enqueue(new Callback<ApiResponse<DonationSite>>() {
            @Override
            public void onResponse(Call<ApiResponse<DonationSite>> call, Response<ApiResponse<DonationSite>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    donationSites.clear();
                    donationSites.addAll(response.body().getData()); // Extract the "data" array
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ManageSites.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    Log.e("API Error", "Response unsuccessful or empty body");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<DonationSite>> call, Throwable t) {
                Toast.makeText(ManageSites.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", t.getMessage(), t);
            }
        });
    }
}
