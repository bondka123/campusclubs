package com.example.campusclubs.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.campusclubs.models.Club;
import com.example.campusclubs.models.User;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "campusclubs.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table users
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE," +
                "password TEXT," +
                "role TEXT)");

        // Table clubs
        db.execSQL("CREATE TABLE clubs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "description TEXT," +
                "category TEXT)");

        // Table enrollments (inscriptions)
        db.execSQL("CREATE TABLE enrollments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "club_id INTEGER)");

        // Insérer un admin par défaut
        ContentValues admin = new ContentValues();
        admin.put("username", "admin");
        admin.put("password", "admin");
        admin.put("role", "admin");
        db.insert("users", null, admin);

        // Insérer un étudiant de test
        ContentValues student = new ContentValues();
        student.put("username", "student");
        student.put("password", "student");
        student.put("role", "student");
        db.insert("users", null, student);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS enrollments");
        db.execSQL("DROP TABLE IF EXISTS clubs");
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    // Authentification
    public User login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE username=? AND password=?",
                new String[]{username, password}
        );
        if (cursor != null && cursor.moveToFirst()) {
            User u = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("username")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    cursor.getString(cursor.getColumnIndexOrThrow("role"))
            );
            cursor.close();
            return u;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    // CRUD Clubs
    public long addClub(String name, String description, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("category", category);
        return db.insert("clubs", null, values);
    }

    public int updateClub(int id, String name, String description, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("category", category);
        return db.update("clubs", values, "id=?", new String[]{String.valueOf(id)});
    }

    public int deleteClub(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("clubs", "id=?", new String[]{String.valueOf(id)});
    }

    public List<Club> getAllClubs(String query) {
        List<Club> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if (query == null || query.isEmpty()) {
            cursor = db.rawQuery("SELECT * FROM clubs", null);
        } else {
            String q = "%" + query + "%";
            cursor = db.rawQuery("SELECT * FROM clubs WHERE name LIKE ? OR category LIKE ?",
                    new String[]{q, q});
        }

        if (cursor.moveToFirst()) {
            do {
                Club c = new Club(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getString(cursor.getColumnIndexOrThrow("category"))
                );
                list.add(c);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public Club getClubById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM clubs WHERE id=?",
                new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            Club c = new Club(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    cursor.getString(cursor.getColumnIndexOrThrow("category"))
            );
            cursor.close();
            return c;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    // Inscriptions
    public boolean isEnrolled(int userId, int clubId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM enrollments WHERE user_id=? AND club_id=?",
                new String[]{String.valueOf(userId), String.valueOf(clubId)});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public long enroll(int userId, int clubId) {
        if (isEnrolled(userId, clubId)) return -1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("club_id", clubId);
        return db.insert("enrollments", null, values);
    }

    public List<Club> getClubsForUser(int userId) {
        List<Club> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT c.id, c.name, c.description, c.category " +
                        "FROM clubs c JOIN enrollments e ON c.id = e.club_id " +
                        "WHERE e.user_id=?",
                new String[]{String.valueOf(userId)}
        );
        if (cursor.moveToFirst()) {
            do {
                Club c = new Club(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getString(cursor.getColumnIndexOrThrow("category"))
                );
                list.add(c);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
