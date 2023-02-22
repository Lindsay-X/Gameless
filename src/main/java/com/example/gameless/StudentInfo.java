package com.example.gameless;

public class StudentInfo {
    private long studentNumber;
    private String fullName;
    private int points;

    public StudentInfo(long studentNumber, String fullName, int points) {
        this.studentNumber = studentNumber;
        this.fullName = fullName;
        this.points = points;
    }

    public long getStudentNumber() {
        return studentNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public int getPoints() {
        return points;
    }
}
