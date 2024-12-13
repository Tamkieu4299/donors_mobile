//package com.example.as2_blood_donation;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.as2_blood_donation.activities.CreateSite;
//import com.example.as2_blood_donation.activities.Login;
//import com.example.as2_blood_donation.activities.ManageSites;
//import com.example.as2_blood_donation.activities.MapsActivity;
//import com.example.as2_blood_donation.activities.Register;
//import com.example.as2_blood_donation.models.UserSession;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//public class MainActivity extends AppCompatActivity {
//    TextView nameTextView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Button buttonManageSite = findViewById(R.id.buttonManageSite);
//        buttonManageSite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this, ManageSites.class);
//                startActivity(i);
//            }
//        });
//
//        Button buttonCreateSite = findViewById(R.id.buttonCreateSite);
//        buttonCreateSite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this, CreateSite.class);
//                startActivity(i);
//            }
//        });
//
//    }
//
//
//}

package com.example.as2_blood_donation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.as2_blood_donation.activities.CreateSite;
import com.example.as2_blood_donation.activities.ManageSites;
import com.example.as2_blood_donation.activities.MapsActivity;
import com.example.as2_blood_donation.models.UserSession;

public class MainActivity extends AppCompatActivity {
    private TextView nameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        nameTextView = findViewById(R.id.textView2); // Welcome, [User's Name] TextView

        // Set the user's name dynamically
        setUserName();

        // Button for managing sites
        Button buttonManageSite = findViewById(R.id.buttonManageSite);
        buttonManageSite.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ManageSites.class);
            startActivity(intent);
        });

        // Button for creating a site
        Button buttonCreateSite = findViewById(R.id.buttonCreateSite);
        buttonCreateSite.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateSite.class);
            startActivity(intent);
        });

        // Button for opening the map
//        Button buttonMap = findViewById(R.id.buttonMap);
//        buttonMap.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//            startActivity(intent);
//        });

        // Button for viewing profile
//        Button buttonProfile = findViewById(R.id.buttonProfile);
//        buttonProfile.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, ProfileActivity.class); // Assuming there's a ProfileActivity
//            startActivity(intent);
//        });
    }

    private void setUserName() {
        // Retrieve user information from UserSession
        UserSession userSession = UserSession.getInstance();
        String userName = userSession.getFirstName(); // Full name from UserSession

        // If the user name is available, update the TextView
        if (userName != null && !userName.isEmpty()) {
            nameTextView.setText("Welcome, " + userName);
        } else {
            nameTextView.setText("Welcome, User");
        }
    }
}
