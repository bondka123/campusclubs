package com.example.campusclubs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.campusclubs.db.DBHelper;
import com.example.campusclubs.models.User;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin   = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u = etUsername.getText().toString().trim();
                String p = etPassword.getText().toString().trim();

                if (u.isEmpty() || p.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Champs requis", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = dbHelper.login(u, p);
                if (user == null) {
                    Toast.makeText(LoginActivity.this, "Identifiants invalides", Toast.LENGTH_SHORT).show();
                } else {
                    if ("admin".equals(user.getRole())) {
                        Intent i = new Intent(LoginActivity.this, AdminHomeActivity.class);
                        i.putExtra("userId", user.getId());
                        startActivity(i);
                    } else {
                        Intent i = new Intent(LoginActivity.this, StudentHomeActivity.class);
                        i.putExtra("userId", user.getId());
                        startActivity(i);
                    }
                }
            }
        });
    }
}
