package com.example.campusclubs.clubs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.campusclubs.R;
import com.example.campusclubs.db.DBHelper;

public class ClubFormActivity extends AppCompatActivity {

    EditText etName, etDescription, etCategory;
    Button btnSave;
    DBHelper dbHelper;
    int clubId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_form);

        dbHelper = new DBHelper(this);

        etName = findViewById(R.id.etClubName);
        etDescription = findViewById(R.id.etClubDescription);
        etCategory = findViewById(R.id.etClubCategory);
        btnSave = findViewById(R.id.btnSaveClub);

        if (getIntent() != null && getIntent().hasExtra("clubId")) {
            clubId = getIntent().getIntExtra("clubId", -1);
        }

        if (clubId != -1) {
            // Mode édition
            com.example.campusclubs.models.Club c = dbHelper.getClubById(clubId);
            if (c != null) {
                etName.setText(c.getName());
                etDescription.setText(c.getDescription());
                etCategory.setText(c.getCategory());
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String desc = etDescription.getText().toString().trim();
                String cat = etCategory.getText().toString().trim();

                if (name.isEmpty()) {
                    Toast.makeText(ClubFormActivity.this, "Nom requis", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (clubId == -1) {
                    long id = dbHelper.addClub(name, desc, cat);
                    if (id > 0) {
                        Toast.makeText(ClubFormActivity.this, "Club ajouté", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ClubFormActivity.this, "Erreur ajout", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int rows = dbHelper.updateClub(clubId, name, desc, cat);
                    if (rows > 0) {
                        Toast.makeText(ClubFormActivity.this, "Club mis à jour", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ClubFormActivity.this, "Erreur mise à jour", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
