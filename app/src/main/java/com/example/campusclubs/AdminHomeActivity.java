package com.example.campusclubs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.campusclubs.clubs.ClubListActivity;

public class AdminHomeActivity extends AppCompatActivity {

    Button btnManageClubs, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btnManageClubs = findViewById(R.id.btnManageClubs);
        btnLogout = findViewById(R.id.btnLogout);

        btnManageClubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminHomeActivity.this, ClubListActivity.class);
                i.putExtra("isAdmin", true);
                startActivity(i);
            }
        });

        btnLogout.setOnClickListener(v -> {
            finish();
        });
    }
}
