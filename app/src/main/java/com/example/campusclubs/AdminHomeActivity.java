package com.example.campusclubs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.campusclubs.clubs.ClubListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminHomeActivity extends AppCompatActivity {

    Button btnManageClubs, btnLogout;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btnManageClubs = findViewById(R.id.btnManageClubs);
        btnLogout = findViewById(R.id.btnLogout);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        // Set Home as selected on startup
        bottomNavigation.setSelectedItemId(R.id.nav_home);

        btnManageClubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminHomeActivity.this, ClubListActivity.class);
                i.putExtra("isAdmin", true);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(v -> {
            logoutUser();
        });

        // Bottom Navigation Click Listener
        bottomNavigation.setOnItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_chat) {
                startActivity(new Intent(AdminHomeActivity.this, AdminChatActivity.class));
                return true;
            } else if (itemId == R.id.nav_clubs) {
                Intent clubIntent = new Intent(AdminHomeActivity.this, ClubListActivity.class);
                clubIntent.putExtra("isAdmin", true);
                startActivity(clubIntent);
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(AdminHomeActivity.this, AdminProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    private void logoutUser() {
        // Clear user session/preferences if needed
        Intent intent = new Intent(AdminHomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
