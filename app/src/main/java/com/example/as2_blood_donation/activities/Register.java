package com.example.as2_blood_donation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.as2_blood_donation.MainActivity;
import com.example.as2_blood_donation.R;
import com.example.as2_blood_donation.models.Donor;
import com.example.as2_blood_donation.models.enums.BloodType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {

    private EditText fullName, email, password, confirmPassword;
    private Spinner bloodType;
    private Button registerButton;
    private TextView loginRedirect;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        fullName = findViewById(R.id.full_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        registerButton = findViewById(R.id.register_button);
        loginRedirect = findViewById(R.id.login_redirect);
        bloodType = findViewById(R.id.blood_type);

        // Register button click listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullNameText = fullName.getText().toString();
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                String confirmPasswordText = confirmPassword.getText().toString();
                String bloodTypeText = bloodType.getSelectedItem().toString();

                if (TextUtils.isEmpty(fullNameText) || TextUtils.isEmpty(emailText) ||
                        TextUtils.isEmpty(passwordText) || TextUtils.isEmpty(confirmPasswordText)) {
                    Toast.makeText(Register.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (!passwordText.equals(confirmPasswordText)) {
                    Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(emailText, passwordText, fullNameText, bloodTypeText);
                }
            }
        });

        // Login redirect click listener
        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void registerUser(String email, String password, String name, String bloodType) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user != null) {
                            String uID = user.getUid();
                            Donor donorDocument = new Donor(uID, name, name, email, password, bloodType, false);

                            // Save Donor to Firestore
                            db.collection("donors").document(uID)
                                    .set(donorDocument)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("Firestore", "Donor document created successfully!");
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("Firestore", "Error creating Donor document", e);
                                    });
                        }
                        Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();

                        // Navigate back to MainActivity
                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Register.this, "Registration failed:", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}