package com.example.as2_blood_donation.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.as2_blood_donation.R;
import com.example.as2_blood_donation.api.ApiClient;
import com.example.as2_blood_donation.api.ApiService;
import com.example.as2_blood_donation.models.ApiResponse;
import com.example.as2_blood_donation.models.ApiResponseObject;
import com.example.as2_blood_donation.models.RegisterRequest;
import com.example.as2_blood_donation.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI components
        EditText userName = findViewById(R.id.user_name);
        EditText firstName = findViewById(R.id.first_name);
        EditText lastName = findViewById(R.id.last_name);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button registerButton = findViewById(R.id.register_button);
        Spinner roleSpinner = findViewById(R.id.role_spinner);

        // Define roles
        String[] roles = {"Donor", "Admin", "Super Admin"};

        // Create an ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set adapter to Spinner
        roleSpinner.setAdapter(adapter);

        // Get selected role (optional)
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRole = roles[position];
                Log.d("RegisterActivity", "Selected Role: " + selectedRole);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        // Set up register button click
        registerButton.setOnClickListener(view -> {
            String user = userName.getText().toString().trim();
            String first = firstName.getText().toString().trim();
            String last = lastName.getText().toString().trim();
            String emailInput = email.getText().toString().trim();
            String pass = password.getText().toString().trim();

            // Call the register API
            ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
            RegisterRequest userRequest = new RegisterRequest(user, first, last, emailInput, pass, 1, "0834086256", 100, "A",  "donor");

            apiService.registerUser(userRequest).enqueue(new Callback<ApiResponseObject<RegisterRequest>>() {
                @Override
                public void onResponse(Call<ApiResponseObject<RegisterRequest>> call, Response<ApiResponseObject<RegisterRequest>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(Register.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                        finish(); // Navigate back to login
                    } else {
                        Toast.makeText(Register.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponseObject<RegisterRequest>> call, Throwable t) {
                    Toast.makeText(Register.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        });
    }
}
