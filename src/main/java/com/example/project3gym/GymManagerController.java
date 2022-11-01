package com.example.project3gym;

import fitnessmanager.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GymManagerController {
    private MemberDatabase db = new MemberDatabase();
    private ClassSchedule cs = new ClassSchedule();
    @FXML
    private Label registrationOutput;
    private MembershipType membershipType;
    public Button addButton;
    public TextArea fName;
    public TextArea lName;
    public TextArea loc;
    public DatePicker dob;
    public RadioButton standard;
    public RadioButton family;
    public RadioButton premium;



    @FXML
    protected void onAddButtonClick(ActionEvent event) {
        if(standard.isSelected()) {
            membershipType = MembershipType.STANDARD;
        }
        else if(family.isSelected()) {
            membershipType = MembershipType.FAMILY;
        }
        else if(premium.isSelected()) {
            membershipType = MembershipType.PREMIUM;
        }

        Date dobTest = new Date(dob.getValue().getMonthValue(), dob.getValue().getDayOfMonth(), dob.getValue().getYear());

        //registrationOutput.setText(add(fName.getText(), lName.getText(), dobTest, loc.getText(), membershipType));

    }

    @FXML
    protected void onRemoveButtonClick() {
        

    }

    @FXML
    protected void onQuitButtonClick() {
        System.exit(0);
    }

    @FXML
    protected void onDisplayButtonClick() {

    }

    private String add(String fname, String lname, Date dob, String location, MembershipType membershipType){
        String errorMessage = canBeAdded(dob, location);
        if (errorMessage != null){
            return errorMessage;
        }
        Date expire = getExpirationDate(membershipType);
        Member memberToAdd;
        switch (membershipType){
            case STANDARD:
                memberToAdd = new Member(fname, lname, dob, expire, location);
                break;
            case FAMILY:
                memberToAdd = new Family(fname, lname, dob, expire, location);
                break;
            case PREMIUM:
                memberToAdd = new Premium(fname, lname, dob, expire, location);
                break;
            default:
                memberToAdd = null;
        }
        if (this.db.add(memberToAdd)){               //Checks if member has already been added to database
            return (fname + " " + lname + " added.");
        }
        else {
            return (fname + " " + lname + " is already in the database.");
        }
    }

    private String canBeAdded(Date birthday, String location){
        if (!birthday.isValid()){                                                       //Checks if birthday date is a valid date
            return ("DOB " + birthday.toString() + ": invalid calendar date!");
        }
        Date today = new Date();
        if (birthday.compareTo(today) >= 0){                                            //Checks if birthday date is before today
            return ("DOB " + birthday.toString() + ": cannot be today or a future date!");
        }
        if (!isOverEighteen(birthday, today)){                                          //Checks if member is over 18
            return ("DOB " + birthday.toString() + ": must be 18 or older to join!");
        }
        if (Location.stringToLocation(location) == null){                                   //Checks if member's gym location exists
            return (location + ": invalid location!");
        }
        return null;
    }

    private String remove(String fname, String lname, String dob){
        if (this.db.remove(new Member(fname, lname, dob))){                                  //Checks if member exists in the database
            return (fname + " " + lname + " removed.");
        }
        else{
            return (fname + " " + lname + " is not in the database.");
        }
    }

    private Date getExpirationDate(MembershipType membershipType){
        Date today = new Date();
        switch (membershipType){
            case STANDARD:
            case FAMILY:
                int futureMonth = 3 + today.getMonth();
                if (futureMonth > 12){
                    futureMonth = futureMonth - 12;
                    return new Date(futureMonth, today.getDay(), today.getYear() + 1);
                }
                return new Date(futureMonth, today.getDay(), today.getYear());
            case PREMIUM:
                return new Date(today.getMonth(), today.getDay(), today.getYear() + 1);
        }
        return null;
    }

    private boolean isOverEighteen(Date birthday, Date today){
        if (today.getYear() - birthday.getYear() > Constant.MINIMUM_AGE.getValue()){
            return true;
        }
        if (today.getYear() - birthday.getYear() < Constant.MINIMUM_AGE.getValue()){
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