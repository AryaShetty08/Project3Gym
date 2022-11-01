package com.example.project3gym;

import fitnessmanager.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GymManagerController {
    private MemberDatabase db = new MemberDatabase();
    private fitnessmanager.ClassSchedule cs = new fitnessmanager.ClassSchedule();
    @FXML
    private Label tempText;

    @FXML
    protected void onAddButtonClick() {
        tempText.setText("Enter in the following to register: ");
        //add();

    }

    @FXML
    protected void onRemoveButtonClick() {
        tempText.setText("Enter in the following to cancel membership: ");
    }

    @FXML
    protected void onQuitButtonClick() {
        System.exit(0);
    }

    @FXML
    protected void onDisplayButtonClick() {

    }

    private void add(String fname, String lname, String dob, String location, fitnessmanager.MembershipType membershipType){
        if (canBeAdded(dob, location)){
            fitnessmanager.Date expire = getExpirationDate(membershipType);
            fitnessmanager.Member memberToAdd;
            switch (membershipType){
                case STANDARD:
                    memberToAdd = new fitnessmanager.Member(fname, lname, dob, expire, location);
                    break;
                case FAMILY:
                    memberToAdd = new fitnessmanager.Family(fname, lname, dob, expire, location);
                    break;
                case PREMIUM:
                    memberToAdd = new fitnessmanager.Premium(fname, lname, dob, expire, location);
                    break;
                default:
                    memberToAdd = null;
            }
            if (this.db.add(memberToAdd)){               //Checks if member has already been added to database
                System.out.println(fname + " " + lname + " added.");
            }
            else {
                System.out.println(fname + " " + lname + " is already in the database.");
            }
        }
    }

    private boolean canBeAdded(String dob, String location){
        fitnessmanager.Date birthday = new fitnessmanager.Date(dob);
        if (!birthday.isValid()){                                                       //Checks if birthday date is a valid date
            System.out.println("DOB " + dob + ": invalid calendar date!");
            return false;
        }
        fitnessmanager.Date today = new fitnessmanager.Date();
        if (birthday.compareTo(today) >= 0){                                            //Checks if birthday date is before today
            System.out.println("DOB " + dob + ": cannot be today or a future date!");
            return false;
        }
        if (!isOverEighteen(birthday, today)){                                          //Checks if member is over 18
            System.out.println("DOB " + dob + ": must be 18 or older to join!");
            return false;
        }
        if (fitnessmanager.Location.stringToLocation(location) == null){                                   //Checks if member's gym location exists
            System.out.println(location + ": invalid location!");
            return false;
        }
        return true;
    }

    private fitnessmanager.Date getExpirationDate(fitnessmanager.MembershipType membershipType){
        fitnessmanager.Date today = new fitnessmanager.Date();
        switch (membershipType){
            case STANDARD:
            case FAMILY:
                int futureMonth = 3 + today.getMonth();
                if (futureMonth > 12){
                    futureMonth = futureMonth - 12;
                    return new fitnessmanager.Date(futureMonth, today.getDay(), today.getYear() + 1);
                }
                return new fitnessmanager.Date(futureMonth, today.getDay(), today.getYear());
            case PREMIUM:
                return new fitnessmanager.Date(today.getMonth(), today.getDay(), today.getYear() + 1);
        }
        return null;
    }

    private boolean isOverEighteen(fitnessmanager.Date birthday, fitnessmanager.Date today){
        if (today.getYear() - birthday.getYear() > fitnessmanager.Constant.MINIMUM_AGE.getValue()){
            return true;
        }
        if (today.getYear() - birthday.getYear() < fitnessmanager.Constant.MINIMUM_AGE.getValue()){
            return false;
        }
        if (today.getMonth() > birthday.getMonth()){
            return true;
        }
        if (today.getMonth() < birthday.getMonth()){
            return false;
        }
        if (today.getDay() >= birthday.getDay()){
            return true;
        }
        return false;
    }
}