package com.example.campusclubs.clubs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.campusclubs.R;
import com.example.campusclubs.adapters.ClubAdapter;
import com.example.campusclubs.db.DBHelper;
import com.example.campusclubs.models.Club;

import java.util.List;

public class ClubListActivity extends AppCompatActivity {

    ListView listView;
    Button btnAdd;
    EditText etSearch;
    DBHelper dbHelper;
    boolean isAdmin;
    int userId;
    String mode; // "all" ou "my"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);

        dbHelper = new DBHelper(this);

        listView = findViewById(R.id.listViewClubs);
        btnAdd = findViewById(R.id.btnAddClub);
        etSearch = findViewById(R.id.etSearchClub);

        isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        userId = getIntent().getIntExtra("userId", -1);
        mode = getIntent().getStringExtra("mode");

        if (!isAdmin) {
            btnAdd.setVisibility(View.GONE);
        }

        loadClubs("");

        btnAdd.setOnClickListener(v -> {
            Intent i = new Intent(ClubListActivity.this, ClubFormActivity.class);
            startActivity(i);
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable editable) {
                loadClubs(editable.toString());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Club club = (Club) adapterView.getItemAtPosition(position);
                Intent i = new Intent(ClubListActivity.this, ClubDetailsActivity.class);
                i.putExtra("clubId", club.getId());
                i.putExtra("isAdmin", isAdmin);
                i.putExtra("userId", userId);
                startActivity(i);
            }
        });
    }

    private void loadClubs(String query) {
        List<Club> clubs;
        if (!isAdmin && "my".equals(mode) && userId > 0) {
            clubs = dbHelper.getClubsForUser(userId);
        } else {
            clubs = dbHelper.getAllClubs(query);
        }
        ClubAdapter adapter = new ClubAdapter(this, clubs);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadClubs(etSearch.getText().toString());
    }
}
