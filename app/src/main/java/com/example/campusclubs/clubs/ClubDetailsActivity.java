package com.example.campusclubs.clubs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.example.campusclubs.R;
import com.example.campusclubs.db.DBHelper;
import com.example.campusclubs.models.Club;

public class ClubDetailsActivity extends AppCompatActivity {

    TextView tvName, tvDescription, tvCategory;
    Button btnEnroll, btnEdit, btnDelete;
    DBHelper dbHelper;
    int clubId;
    boolean isAdmin;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);

        dbHelper = new DBHelper(this);

        tvName = findViewById(R.id.tvClubName);
        tvDescription = findViewById(R.id.tvClubDescription);
        tvCategory = findViewById(R.id.tvClubCategory);
        btnEnroll = findViewById(R.id.btnEnroll);
        btnEdit = findViewById(R.id.btnEditClub);
        btnDelete = findViewById(R.id.btnDeleteClub);

        clubId = getIntent().getIntExtra("clubId", -1);
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        userId = getIntent().getIntExtra("userId", -1);

        Club c = dbHelper.getClubById(clubId);
        if (c != null) {
            tvName.setText(c.getName());
            tvDescription.setText(c.getDescription());
            tvCategory.setText(c.getCategory());
        }

        if (isAdmin) {
            btnEnroll.setVisibility(View.GONE);
        } else {
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userId == -1) {
                    Toast.makeText(ClubDetailsActivity.this, "Utilisateur invalide", Toast.LENGTH_SHORT).show();
                    return;
                }
                long res = dbHelper.enroll(userId, clubId);
                if (res > 0) {
                    Toast.makeText(ClubDetailsActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ClubDetailsActivity.this, "Déjà inscrit ou erreur", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEdit.setOnClickListener(v -> {
            Intent i = new Intent(ClubDetailsActivity.this, ClubFormActivity.class);
            i.putExtra("clubId", clubId);
            startActivity(i);
        });

        btnDelete.setOnClickListener(v -> {
            int rows = dbHelper.deleteClub(clubId);
            if (rows > 0) {
                Toast.makeText(ClubDetailsActivity.this, "Club supprimé", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(ClubDetailsActivity.this, "Erreur suppression", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // recharger après édition
        Club c = dbHelper.getClubById(clubId);
        if (c != null) {
            tvName.setText(c.getName());
            tvDescription.setText(c.getDescription());
            tvCategory.setText(c.getCategory());
        }
    }
}
