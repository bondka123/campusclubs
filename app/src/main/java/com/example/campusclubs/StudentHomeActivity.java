package com.example.campusclubs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.campusclubs.clubs.ClubListActivity;

public class StudentHomeActivity extends AppCompatActivity {

    Button btnAllClubs, btnMyClubs, btnLogout;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        userId = getIntent().getIntExtra("userId", -1);

        btnAllClubs = findViewById(R.id.btnAllClubs);
        btnMyClubs = findViewById(R.id.btnMyClubs);
        btnLogout = findViewById(R.id.btnLogout);

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

        btnLogout.setOnClickListener(v -> finish());
    }
}
