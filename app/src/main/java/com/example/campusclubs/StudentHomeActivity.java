package com.example.campusclubs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.campusclubs.clubs.ClubListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentHomeActivity extends AppCompatActivity {

    Button btnAllClubs, btnMyClubs, btnLogout;
    BottomNavigationView bottomNavigation;
    int userId;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        userId = getIntent().getIntExtra("userId", -1);
        userName = getIntent().getStringExtra("userName");

        btnAllClubs = findViewById(R.id.btnAllClubs);
        btnMyClubs = findViewById(R.id.btnMyClubs);
        btnLogout = findViewById(R.id.btnLogout);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setSelectedItemId(R.id.nav_home);

        btnAllClubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentHomeActivity.this, ClubListActivity.class);
                i.putExtra("isAdmin", false);
                i.putExtra("userId", userId);
                i.putExtra("mode", "all");
                startActivity(i);
            }
        });

        btnMyClubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentHomeActivity.this, ClubListActivity.class);
                i.putExtra("isAdmin", false);
                i.putExtra("userId", userId);
                i.putExtra("mode", "my");
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(v -> logoutUser());

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_chat) {
                Intent chatIntent = new Intent(StudentHomeActivity.this, StudentChatActivity.class);
                chatIntent.putExtra("userId", userId);
                chatIntent.putExtra("userName", userName);
                startActivity(chatIntent);
                return true;
            } else if (itemId == R.id.nav_clubs) {
                Intent clubIntent = new Intent(StudentHomeActivity.this, ClubListActivity.class);
                clubIntent.putExtra("isAdmin", false);
                clubIntent.putExtra("userId", userId);
                clubIntent.putExtra("mode", "all");
                startActivity(clubIntent);
                return true;
            } else if (itemId == R.id.nav_profile) {
                // TODO: Create StudentProfileActivity
                return true;
            }
            return false;
        });
    }

    private void logoutUser() {
        Intent intent = new Intent(StudentHomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
    }
}
