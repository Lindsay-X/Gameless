package com.example.gameless;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class EventStudentInfo {

    private SimpleStringProperty studentNumber;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleIntegerProperty grade;
    //private SimpleBooleanProperty showedUp;

    @FXML
    private CheckBox showedUp;

    public EventStudentInfo(String studentId, String firstName, String lastName, int grade, boolean showedUp) {
        //initializes a property with a specified value
        this.studentNumber = new SimpleStringProperty(studentId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.grade = new SimpleIntegerProperty(grade);
        this.showedUp = new CheckBox();
    }

    public String getStudentNumber() {
        return studentNumber.get();
    }

    public void setStudentNumber(String studentId) {
        this.studentNumber = new SimpleStringProperty(studentId);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName = new SimpleStringProperty(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName = new SimpleStringProperty(lastName);
    }

    public int getGrade() {
        return grade.get();
    }

    public void setGrade(int grade) {
        this.grade = new SimpleIntegerProperty(grade);
    }

    /*public boolean getShowedUp() {
        return showedUp.get();
    }

    public void setShowedUp(boolean showedUp) {
        this.showedUp = new SimpleBooleanProperty(showedUp);
    }*/

    public CheckBox getShowedUp() {
        return showedUp;
    }

    public void setShowedUp(boolean showedUp) {
        this.showedUp.setSelected(showedUp);
    }
}