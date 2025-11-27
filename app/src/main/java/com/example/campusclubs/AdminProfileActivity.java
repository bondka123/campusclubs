package com.example.campusclubs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.campusclubs.clubs.ClubListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        TextView tvProfile = findViewById(R.id.tvProfile);
        tvProfile.setText("Profil Admin\n\nCette page affiche le profil de l'administrateur.");

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> logoutUser());

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.nav_profile);

        bottomNavigation.setOnItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(AdminProfileActivity.this, AdminHomeActivity.class));
                    finish();
                    return true;
                case R.id.nav_chat:
                    startActivity(new Intent(AdminProfileActivity.this, AdminChatActivity.class));
                    finish();
                    return true;
                case R.id.nav_clubs:
                    Intent clubIntent = new Intent(AdminProfileActivity.this, ClubListActivity.class);
                    clubIntent.putExtra("isAdmin", true);
                    startActivity(clubIntent);
                    finish();
                    return true;
                case R.id.nav_profile:
                    return true;
            }
            return false;
        });
    }

    private void logoutUser() {
        Intent intent = new Intent(AdminProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
