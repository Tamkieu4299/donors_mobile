package com.example.as2_blood_donation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.as2_blood_donation.MainActivity;
import com.example.as2_blood_donation.R;
import com.example.as2_blood_donation.models.UserSession;
import com.example.as2_blood_donation.models.enums.UserRole;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    private EditText email, password;
    private Button loginButton;
    private TextView registerRedirect;
    private TextView appTitle;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null && UserSession.getInstance().getUserId() != null) {
            populateUserSession(currentUser);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerRedirect = findViewById(R.id.textView);
        appTitle = findViewById(R.id.app_title);
        // Login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                if (TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText)) {
                    Toast.makeText(Login.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(emailText, passwordText);
                }
            }
        });

        // Navigate to RegisterActivity
        registerRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        appTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("Login", "Login success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        populateUserSession(user);


                    } else {
                        Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateUserSession(FirebaseUser currentUser) {
        if (currentUser == null) {
            Log.d("Login", "Current user is null");
            return;
        }

        String uid = currentUser.getUid();

        // Check the Donors collection first
        db.collection("donors").document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Donor found
                String name = task.getResult().getString("name");
                String email = currentUser.getEmail();

                // Populate UserSession for Donor
                UserSession session = UserSession.getInstance();
                session.setUserId(uid);
                session.setFullName(name);
                session.setEmail(email);
                session.setRole(UserRole.DONOR);

                Log.d("Login", "Donor user added to session: " + name);
            } else {
                // If not a donor, check the SiteManagers collection
                db.collection("siteManagers").document(uid).get().addOnCompleteListener(siteManagerTask -> {
                    if (siteManagerTask.isSuccessful() && siteManagerTask.getResult().exists()) {
                        // Site Manager found
                        String name = siteManagerTask.getResult().getString("name");
                        String email = currentUser.getEmail();

                        // Populate UserSession for Site Manager
                        UserSession session = UserSession.getInstance();
                        session.setUserId(uid);
                        session.setFullName(name);
                        session.setEmail(email);
                        session.setRole(UserRole.SITE_MANAGER);

                        Log.d("Login", "Site manager user added to session: " + name);
                    } else {
                        // No valid role found
                        Log.d("Login", "User role not recognized.");
                        Toast.makeText(Login.this, "User role not recognized.", Toast.LENGTH_SHORT).show();
                        mAuth.signOut(); // Sign out invalid users
                    }
                });
            }

            // Navigate to MainActivity
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

}
