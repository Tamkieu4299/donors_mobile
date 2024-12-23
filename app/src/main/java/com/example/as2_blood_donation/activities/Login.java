package com.example.as2_blood_donation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.as2_blood_donation.MainActivity;
import com.example.as2_blood_donation.R;
import com.example.as2_blood_donation.api.ApiClient;
import com.example.as2_blood_donation.api.ApiService;
import com.example.as2_blood_donation.models.ApiResponseObject;
import com.example.as2_blood_donation.models.LoginRequest;
import com.example.as2_blood_donation.models.UserSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText email, password;
    private Button loginButton;
    private TextView registerRedirect;
    private TextView appTitle;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize API Service
        apiService = ApiClient.getApiClient().create(ApiService.class);

        // Initialize UI elements
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerRedirect = findViewById(R.id.textView);
        appTitle = findViewById(R.id.app_title);

        // Login button click listener
        loginButton.setOnClickListener(v -> {
            String emailText = email.getText().toString();
            String passwordText = password.getText().toString();

            if (TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText)) {
                Toast.makeText(Login.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(emailText, passwordText);
            }
        });

        // Initialize the register button
        Button registerButton = findViewById(R.id.register_button);

        // Navigate to the Register activity
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });

        appTitle.setOnClickListener(view -> finish());
    }

    private void loginUser(String email, String password) {
        // Prepare the login request body
        // Create the LoginRequest object
        LoginRequest loginRequest = new LoginRequest(email, password);
        apiService.login(loginRequest).enqueue(new Callback<ApiResponseObject<UserSession>>() {
            @Override
            public void onResponse(Call<ApiResponseObject<UserSession>> call, Response<ApiResponseObject<UserSession>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponseObject<UserSession> apiResponse = response.body();
                    if ("success".equals(apiResponse.getStatus()) && apiResponse.getData() != null) {
                        UserSession session = apiResponse.getData();
                        populateUserSession(session);
                    } else {
                        Toast.makeText(Login.this, "Invalid login credentials.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponseObject<UserSession>> call, Throwable t) {
                Toast.makeText(Login.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Error", t.getMessage(), t);
            }
        });
    }

    private void populateUserSession(UserSession session) {
        // Populate the singleton UserSession with API response data
        UserSession userSession = UserSession.getInstance();
        userSession.setId(session.getId());
        userSession.setUserName(session.getUserName());
        userSession.setFirstName(session.getFirstName());
        userSession.setLastName(session.getLastName());
        userSession.setPhone(session.getPhone());
        userSession.setEmail(session.getEmail());
        userSession.setToken(session.getToken());
        userSession.setTokenType(session.getTokenType());
        userSession.setSumOfDonatedBloods(session.getSumOfDonatedBloods());
        userSession.setTypeOfBlood(session.getTypeOfBlood());

        Log.d("Login", "User session populated: " + userSession.toString());

        // Navigate to MainActivity
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
