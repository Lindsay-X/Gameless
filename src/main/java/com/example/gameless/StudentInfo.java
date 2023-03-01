package com.example.gameless;

public class StudentInfo {
    private String studentNumber;
    private String firstName;
    private String lastName;
    private int grade;
    private int points;

    public StudentInfo(String studentNumber, String firstName, String lastName, int grade, int points) {
        this.studentNumber = studentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade = grade;
        this.points = points;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public int getPoints() {
        return points;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getGrade() {
        return grade;
    }
}
