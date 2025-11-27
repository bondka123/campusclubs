package com.example.campusclubs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campusclubs.clubs.ClubListActivity;
import com.example.campusclubs.db.DBHelper;
import com.example.campusclubs.models.Message;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class StudentChatActivity extends AppCompatActivity {

    private EditText etMessage;
    private Button btnSend;
    private LinearLayout messagesContainer;
    private BottomNavigationView bottomNavigation;
    private DBHelper dbHelper;
    private int userId;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_chat);

        userId = getIntent().getIntExtra("userId", -1);
        userName = getIntent().getStringExtra("userName");

        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        messagesContainer = findViewById(R.id.messagesContainer);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        Button btnLogout = findViewById(R.id.btnLogout);

        dbHelper = new DBHelper(this);

        btnLogout.setOnClickListener(v -> logoutUser());
        btnSend.setOnClickListener(v -> sendMessage());

        bottomNavigation.setSelectedItemId(R.id.nav_chat);
        setupBottomNavigation();

        loadMessages();
    }

    private void sendMessage() {
        String messageText = etMessage.getText().toString().trim();
        if (messageText.isEmpty()) {
            Toast.makeText(this, "Veuillez écrire un message", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get admin user (assuming admin has id 1)
        int adminId = 1;
        String adminName = "Admin";

        dbHelper.addMessage(userId, userName, adminId, adminName, messageText, "student");
        etMessage.setText("");
        loadMessages();
        Toast.makeText(this, "Message envoyé", Toast.LENGTH_SHORT).show();
    }

    private void loadMessages() {
        messagesContainer.removeAllViews();
        List<Message> messages = dbHelper.getMessagesForStudent(userId);

        for (Message message : messages) {
            addMessageView(message);
        }
    }

    private void addMessageView(Message message) {
        LinearLayout messageLayout = new LinearLayout(this);
        messageLayout.setOrientation(LinearLayout.VERTICAL);
        messageLayout.setPadding(16, 8, 16, 8);

        boolean isFromMe = message.getSenderId() == userId;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 4, 0, 4);
        messageLayout.setLayoutParams(params);

        // Sender name
        TextView senderView = new TextView(this);
        senderView.setText(isFromMe ? "Vous:" : message.getSenderName() + ":");
        senderView.setTextSize(12);
        senderView.setTextColor(isFromMe ? 0xFF0066CC : 0xFF666666);
        messageLayout.addView(senderView);

        // Message text
        TextView messageView = new TextView(this);
        messageView.setText(message.getMessageText());
        messageView.setTextSize(14);
        messageView.setPadding(12, 8, 12, 8);
        messageView.setBackgroundColor(isFromMe ? 0xFFE3F2FD : 0xFFF5F5F5);
        messageLayout.addView(messageView);

        // Timestamp
        TextView timeView = new TextView(this);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
        timeView.setText(sdf.format(new java.util.Date(message.getTimestamp())));
        timeView.setTextSize(10);
        timeView.setTextColor(0xFF999999);
        messageLayout.addView(timeView);

        messagesContainer.addView(messageLayout);
    }

    private void setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(StudentChatActivity.this, StudentHomeActivity.class)
                        .putExtra("userId", userId));
                finish();
                return true;
            } else if (itemId == R.id.nav_chat) {
                return true;
            } else if (itemId == R.id.nav_clubs) {
                Intent clubIntent = new Intent(StudentChatActivity.this, ClubListActivity.class);
                clubIntent.putExtra("isAdmin", false);
                clubIntent.putExtra("userId", userId);
                clubIntent.putExtra("mode", "all");
                startActivity(clubIntent);
                finish();
                return true;
            } else if (itemId == R.id.nav_profile) {
                // TODO: Create StudentProfileActivity
                Toast.makeText(this, "Profil - À venir", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
    }

    private void logoutUser() {
        Intent intent = new Intent(StudentChatActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMessages();
    }
}
