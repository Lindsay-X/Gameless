package com.example.gameless;

public class StudentInfo {
    private String studentNumber;
    private String firstName;
    private String lastName;
    private int grade;
    private int points;

<<<<<<< HEAD
    public StudentInfo(String studentNumber, String fullName, int points) {
        //Constructor method takes the student information
=======
    public StudentInfo(String studentNumber, String firstName, String lastName, int grade, int points) {
>>>>>>> 42572a58659f29c4cc8a975adb4f4d93d996359e
        this.studentNumber = studentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade = grade;
        this.points = points;
    }
    //Gets method and returns value
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
