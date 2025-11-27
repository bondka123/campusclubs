package com.example.campusclubs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.campusclubs.clubs.ClubListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chat);

        TextView tvChat = findViewById(R.id.tvChat);
        tvChat.setText("Chat avec les utilisateurs\n\nCette page permet aux admins de communiquer avec les utilisateurs.");

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> logoutUser());

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.nav_chat);

        bottomNavigation.setOnItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(AdminChatActivity.this, AdminHomeActivity.class));
                    finish();
                    return true;
                case R.id.nav_chat:
                    return true;
                case R.id.nav_clubs:
                    Intent clubIntent = new Intent(AdminChatActivity.this, ClubListActivity.class);
                    clubIntent.putExtra("isAdmin", true);
                    startActivity(clubIntent);
                    finish();
                    return true;
                case R.id.nav_profile:
                    startActivity(new Intent(AdminChatActivity.this, AdminProfileActivity.class));
                    finish();
                    return true;
            }
            return false;
        });
    }

    private void logoutUser() {
        Intent intent = new Intent(AdminChatActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
