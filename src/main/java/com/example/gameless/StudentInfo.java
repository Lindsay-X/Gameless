package com.example.gameless;

public class StudentInfo {
    private String studentNumber;
    private String fullName;
    private int points;

    public StudentInfo(String studentNumber, String fullName, int points) {
        this.studentNumber = studentNumber;
        this.fullName = fullName;
        this.points = points;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public int getPoints() {
        return points;
    }
}
