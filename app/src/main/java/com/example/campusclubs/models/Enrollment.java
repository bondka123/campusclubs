package com.example.campusclubs.models;

public class Enrollment {
    private int id;
    private int userId;
    private int clubId;

    public Enrollment() {}

    public Enrollment(int id, int userId, int clubId) {
        this.id = id;
        this.userId = userId;
        this.clubId = clubId;
    }

    // Getters / Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getClubId() { return clubId; }
    public void setClubId(int clubId) { this.clubId = clubId; }
}
