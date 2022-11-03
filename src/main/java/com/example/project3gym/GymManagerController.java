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
    public Label printOutput;
    @FXML
    private Label unenrollOutput;
    public Label checkOutput;
    public TextArea fName;
    public TextArea fName1;
    public TextArea fName2;
    public TextArea lName;
    public TextArea lName1;
    public TextArea lName2;
    public TextArea loc;
    public TextArea loc2;
    public TextArea className;
    public TextArea instructor;
    public DatePicker dob;
    public DatePicker dob1;
    public DatePicker dob2;
    public RadioButton standard;
    public RadioButton family;
    public RadioButton premium;
    public RadioButton unsortedList;
    public RadioButton countyList;
    public RadioButton nameList;
    public RadioButton expireList;
    public RadioButton normalList;
    public RadioButton feeList;
    public RadioButton fitnessList;
    public CheckBox guest;

    @FXML
    protected void onPrintButtonClick(){
        if(unsortedList.isSelected()){
            printOutput.setText(printMemberList());
        }
        else if(countyList.isSelected()){
            printOutput.setText(printSortedMemberList(SortCategory.COUNTY));
        }
        else if(nameList.isSelected()){
            printOutput.setText(printSortedMemberList(SortCategory.NAME));
        }
        else if(expireList.isSelected()){
            printOutput.setText(printSortedMemberList(SortCategory.EXPIRATION_DATE));
        }
        else if(normalList.isSelected()){

        }
        else if(feeList.isSelected()){
            printOutput.setText(printMemberWithFees());
        }
        else if(fitnessList.isSelected()){
            //checkOutput.setText(printFitnessClass());
        }
    }

    /**
     * Prints out unsorted list of members
     * Checks if list is empty,
     * if not, prints unsorted list of members
     */
    private String printMemberList(){
        String toReturn = "";
        if (this.db.isEmpty()){
            return "Member Database is empty!" + "\n";
        }
        toReturn = toReturn + "-list of members-" + "\n";
        toReturn = toReturn + this.db.print() + "\n";
        toReturn = toReturn + "-end of list-" + "\n";
        return toReturn;
    }

    /**
     * Prints out sorted list of members ordered by county their gym is in (alphabetically),
     * Sorts can be based on
     * - county and zip code
     * - membership expiration date
     * - name of members
     * First checks if list is empty,
     * if not, prints list sorted by input parameter
     * @param category the category the members are sorted by
     */
    private String printSortedMemberList(SortCategory category){
        if (this.db.isEmpty()){
            return ("Member Database is empty!") + "\n";
        }
        String toReturn = "";
        switch (category){
            case COUNTY:
                toReturn = toReturn + ("-list of members sorted by county and zipcode-") + "\n";
                toReturn = toReturn + this.db.sortedPrint(SortCategory.COUNTY);
                break;
            case NAME:
                toReturn = toReturn + ("-list of members sorted by last name, and first name-") + "\n";
                toReturn = toReturn + this.db.sortedPrint(SortCategory.NAME);
                break;
            case EXPIRATION_DATE:
                toReturn = toReturn + ("-list of members sorted by membership expiration date-") + "\n";
                toReturn = toReturn + this.db.sortedPrint(SortCategory.EXPIRATION_DATE);
                break;
        }
        toReturn = toReturn + ("-end of list-") + "\n";
        return toReturn;
    }

    /**
     * Prints out unsorted list of members with their fees and guest passes
     * Checks if list is empty,
     * if not, prints unsorted list of members with fees
     */
    private String printMemberWithFees(){
        if (this.db.isEmpty()){
            return ("Member Database is empty!") + "\n";
        }
        String toReturn = "";
        toReturn = toReturn + ("-list of members with membership fees-") + "\n";
        toReturn = toReturn + this.db.printWithFees();
        toReturn = toReturn + ("-end of list-") + "\n";
        return toReturn
    }

    /**
     * Prints the fitness class schedule
     * Checks if class schedule is empty
     * If not, prints class schedule
     */
    private String printSchedule(){
        String toReturn = "";
        if (cs.isEmpty()){
            return ("Fitness class schedule is empty") + "\n";
        }
        toReturn = toReturn + ("-Fitness classes-") + "\n";
        toReturn = toReturn + cs.printClasses();
        toReturn = toReturn + ("-end of class list.") + "\n";
        return toReturn
    }

    @FXML
    protected void onAddButtonClick(ActionEvent event) {
        if(checkAddRequirements()) {
            if (standard.isSelected()) {
                membershipType = MembershipType.STANDARD;
            } else if (family.isSelected()) {
                membershipType = MembershipType.FAMILY;
            } else if (premium.isSelected()) {
                membershipType = MembershipType.PREMIUM;
            }

            Date dobTest = new Date(dob.getValue().getMonthValue(), dob.getValue().getDayOfMonth(), dob.getValue().getYear());

            registrationOutput.setText(add(fName.getText(), lName.getText(), dobTest, loc.getText(), membershipType));
        }
    }

    private boolean checkAddRequirements() {
        if(fName.getText().isEmpty()) {
            registrationOutput.setText("Please enter your first name");
            return false;
        }
        else if(lName.getText().isEmpty()) {
            registrationOutput.setText("Please enter your last name");
            return false;
        }
        else if(dob.getValue() == null) {
            registrationOutput.setText("Please enter in a valid date of birth");
            return false;
        }
        else if(!(standard.isSelected() || family.isSelected() || premium.isSelected()))
        {
            registrationOutput.setText("Please choose a membership");
            return false;
        }
        else if(loc.getText().isEmpty()) {
            registrationOutput.setText("Please enter in a gym location");
            return false;
        }
        return true;
    }

    @FXML
    protected void onRemoveButtonClick() {
        if(checkRemoveRequirements()) {
            Date dobTest = new Date(dob1.getValue().getMonthValue(), dob1.getValue().getDayOfMonth(), dob1.getValue().getYear());

            unenrollOutput.setText(remove(fName1.getText(), lName1.getText(), dobTest.toString()));
        }
    }

    private boolean checkRemoveRequirements() {
        if(fName1.getText().isEmpty()) {
            unenrollOutput.setText("Please enter your first name");
            return false;
        }
        else if(lName1.getText().isEmpty()) {
            unenrollOutput.setText("Please enter your last name");
            return false;
        }
        else if(dob1.getValue() == null) {
            unenrollOutput.setText("Please enter in a valid date of birth");
            return false;
        }
        return true;
    }

    @FXML
    protected void onCheckInButtonClick(){
        if (checkCheckRequirements()){
            Date dobTest = new Date(dob2.getValue().getMonthValue(), dob2.getValue().getDayOfMonth(), dob2.getValue().getYear());
            if (!guest.isSelected()){
                checkOutput.setText(checkIn(className.getText(), instructor.getText(), loc2.getText(), fName2.getText(), lName2.getText(), dobTest.toString()));
            }
            else {
                checkOutput.setText(checkInGuest(className.getText(), instructor.getText(), loc2.getText(), fName2.getText(), lName2.getText(), dobTest.toString()));
            }
        }
    }

    @FXML
    protected void onCheckOutButtonClick(){
        if (checkCheckRequirements()){
            Date dobTest = new Date(dob2.getValue().getMonthValue(), dob2.getValue().getDayOfMonth(), dob2.getValue().getYear());
            if (!guest.isSelected()){
                checkOutput.setText(checkOut(className.getText(), instructor.getText(), loc2.getText(), fName2.getText(), lName2.getText(), dobTest.toString()));
            }
            else {
                checkOutput.setText(checkOutGuest(className.getText(), instructor.getText(), loc2.getText(), fName2.getText(), lName2.getText(), dobTest.toString()));
            }
        }
    }

    private boolean checkCheckRequirements() {
        if(fName2.getText().isEmpty()) {
            checkOutput.setText("Please enter your first name");
            return false;
        }
        else if(lName2.getText().isEmpty()) {
            checkOutput.setText("Please enter your last name");
            return false;
        }
        else if(dob2.getValue() == null) {
            checkOutput.setText("Please enter in a valid date of birth");
            return false;
        }
        else if(loc2.getText().isEmpty()) {
            checkOutput.setText("Please enter your membership location");
            return false;
        }
        else if(className.getText().isEmpty()) {
            checkOutput.setText("Please enter class type (e.g. Pilates)");
            return false;
        }
        else if(instructor.getText().isEmpty()) {
            checkOutput.setText("Please enter instructor name");
            return false;
        }
        return true;
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

    /**
     * Checks in member to fitness class
     * First checks if fitness class and member conditions are valid
     * Then checks if member is enrolled in a class with conflicting time
     * If member is standard (i.e. not instanceof Family), check if
     * class location to check into is valid
     * If all conditions above are met, checks member into class if
     * not checked in or says member is already checked in
     * @param className name of the class type to check into (ex: Pilates or Spinning)
     * @param instructorName name of the instructor of the class to check into
     * @param location location of the class to check into
     * @param fname first name of the member to check in
     * @param lname last name of the member to check in
     * @param dob birthday of the member to check in
     */
    private String checkIn(String className, String instructorName, String location, String fname, String lname, String dob){
        String nonValidFitnessClassConditions = validFitnessClassConditions(className, instructorName, location, fname, lname, dob);
        if (nonValidFitnessClassConditions != null){
            return nonValidFitnessClassConditions;
        }
        String existingClassConflict = classConflict(className, instructorName, location, fname, lname, dob);
        if (existingClassConflict != null){
            return existingClassConflict;
        }
        Member memberToCheckIn = this.db.findMember(new Member(fname, lname, dob));
        if (!(memberToCheckIn instanceof Family)){
            if (!memberToCheckIn.getLocation().equals(Location.stringToLocation(location))){
                Location restrictedLocation = Location.stringToLocation(location);
                return(fname + " " + lname + " checking in " + restrictedLocation.toString()
                        + " - standard membership location restriction.");
            }
        }
        FitnessClass classToEnrollIn = this.cs.classExists(new FitnessClass(className, instructorName, location));
        if (classToEnrollIn.checkIn(memberToCheckIn)){
            return (fname + " " + lname + " checked in ") + printFitnessClass(classToEnrollIn);
        }
        else {
            return (fname + " " + lname + " already checked in.");
        }
    }

    /**
     * Drops member from fitness class
     * First checks if fitness class and member conditions are valid
     * If conditions are valid, drops member from class if
     * they're checked in or says member is not checked in
     * @param className name of the class type to drop (ex: Pilates or Spinning)
     * @param instructorName name of the instructor of the class to drop
     * @param location location of the class to drop
     * @param fname first name of the member to drop from class
     * @param lname last name of the member to drop from class
     * @param dob birthday of the member to drop from class
     */
    private String checkOut(String className, String instructorName, String location, String fname, String lname, String dob) {
        String nonValidFitnessClassConditions = validFitnessClassConditions(className, instructorName, location, fname, lname, dob);
        if (nonValidFitnessClassConditions != null){
            return nonValidFitnessClassConditions;
        }
        FitnessClass classToDrop = cs.classExists(new FitnessClass(className, instructorName, location));
        Member memberToDrop = this.db.findMember(new Member(fname, lname, dob));
        if(classToDrop.find(memberToDrop) != null){
            classToDrop.checkOut(memberToDrop);
            return (fname + " " + lname + " done with the class.");
        }
        return (fname + " " + lname + " did not check in.");
    }

    /**
     * Checks in member's guest to fitness class
     * First checks if fitness class and member conditions are valid
     * Then checks if member is a standard member
     * If member is standard, they can't check in a guest
     * Then checks if class location for guest to check into is valid
     * Then checks if member has guest passes (casts to confirm member is
     * premium or family)
     * If all conditions above are met, checks guest into class
     * @param className name of the class type to check guest into (ex: Pilates or Spinning)
     * @param instructorName name of the instructor of the class to check guest into
     * @param location location of the class to check guest into
     * @param fname first name of the member with guest to check in
     * @param lname last name of the member with guest to check in
     * @param dob birthday of the member with guest to check in
     */
    private String checkInGuest(String className, String instructorName, String location, String fname, String lname, String dob){
        String nonValidFitnessClassConditions = validFitnessClassConditions(className, instructorName, location, fname, lname, dob);
        if (nonValidFitnessClassConditions != null){
            return nonValidFitnessClassConditions;
        }
        Member memberWithGuest = this.db.findMember(new Member(fname, lname, dob));

        if(!(memberWithGuest instanceof Family)){
            return ("Standard membership - guest check-in is not allowed.");
        }

        if (!Location.stringToLocation(location).equals(memberWithGuest.getLocation())){
            return (fname + " " + lname + " Guest checking in " + Location.stringToLocation(location).toString()
                    + " - guest location restriction.");
        }

        boolean havePass = false;
        if(memberWithGuest instanceof Premium){
            havePass = ((Premium) memberWithGuest).useGuestPass();
        }
        else if(memberWithGuest instanceof Family){
            havePass = ((Family) memberWithGuest).useGuestPass();
        }
        if (!havePass){
            return (fname + " " + lname + " ran out of guest pass.");
        }
        FitnessClass classToEnrollIn = this.cs.classExists(new FitnessClass(className, instructorName, location));
        classToEnrollIn.checkInGuest(memberWithGuest);
        return (fname + " " + lname + " (guest) checked in ") + printFitnessClass(classToEnrollIn);
    }

    /**
     * Drops guest from fitness class
     * First checks if fitness class and member conditions are valid
     * If conditions are valid, drops guest from class if
     * they're checked in or says guest is not checked in
     * @param className name of the class type to drop (ex: Pilates or Spinning)
     * @param instructorName name of the instructor of the class to drop
     * @param location location of the class to drop
     * @param fname first name of the member to drop from class
     * @param lname last name of the member to drop from class
     * @param dob birthday of the member to drop from class
     */
    private String checkOutGuest(String className, String instructorName, String location, String fname, String lname, String dob){
        String nonValidFitnessClassConditions = validFitnessClassConditions(className, instructorName, location, fname, lname, dob);
        if (nonValidFitnessClassConditions != null){
            return nonValidFitnessClassConditions;
        }
        Member memberWithGuest = this.db.findMember(new Member(fname, lname, dob));
        FitnessClass classToDrop = this.cs.classExists(new FitnessClass(className, instructorName, location));
        if(classToDrop.findGuest(memberWithGuest) != null){
            classToDrop.checkOutGuest(memberWithGuest);
            if(memberWithGuest instanceof Premium){
                ((Premium) memberWithGuest).returnGuestPass();
            }
            else {
                ((Family) memberWithGuest).returnGuestPass();
            }
            return (fname + " " + lname + " Guest done with the class.");
        }
        return (fname + " " + lname + " Guest did not check in.");
    }

    /**
     * Checks if fitness class conditions are valid to check in or drop
     * Conditions include:
     * - Birthday of member must be a valid date
     * - Instructor of class to check into must exist
     * - Member must exist in member database
     * - Class Type to check into must exist
     * - Member must have non-expired membership
     * - Location of class to check into must exist
     * - Class with instructor, class type and location
     *      must exist in class schedule
     * @param className name of the class type whose conditions to check
     * @param instructorName name of the instructor whose conditions to check
     * @param location name of the location whose conditions to check
     * @param fname first name of the member whose conditions to check
     * @param lname last name of the member whose conditions to check
     * @param dob birthday of the member whose conditions to check
     * @return true if member and class meet conditions
     *         false otherwise
     */
    private String validFitnessClassConditions(String className, String instructorName, String location, String fname, String lname, String dob) {
        Date birthday = new Date(dob);
        if (!birthday.isValid()) {                                                       //Checks if birthday date is a valid date
            return ("DOB " + dob + ": invalid calendar date!");
        }

        if (Instructor.stringToInstructor(instructorName) == null) {
            return (instructorName + " - instructor does not exist.");
        }

        Member memberToCheckIn = this.db.findMember(new Member(fname, lname, dob));
        if (memberToCheckIn == null) {
            return (fname + " " + lname + " " + dob + " is not in the database.");
        }

        if (ClassName.stringToClassName(className) == null) {
            return (className + " class does not exist.");
        }

        if (memberToCheckIn.getExpire().compareTo(new Date()) <= 0) {
            return (fname + " " + lname + " " + dob + " membership expired.");
        }

        if (Location.stringToLocation(location) == null) {
            return (location + " - invalid location.");
        }

        if (cs.classExists(new FitnessClass(className, instructorName, location)) == null) {
            return (className + " by " + instructorName + " does not exist at " + location);
        }
        return null;
    }

    /**
     * Checks if member is enrolled in a class with a conflicting time
     * First checks that member is already enrolled in a class different
     * from class to enroll in.
     * If so, compares times of the two classes
     * @param className name of the class type of class to enroll in
     * @param instructorName name of the instructor of class to enroll in
     * @param location name of the location of class to enroll in
     * @param fname first name of the member to enroll in a fitness class
     * @param lname last name of the member enroll in a fitness class
     * @param dob birthday of the member enroll in a fitness class
     * @return true if time conflict exists
     *         false otherwise
     */
    private String classConflict(String className, String instructorName, String location, String fname, String lname, String dob){
        FitnessClass classToEnrollIn = cs.classExists(new FitnessClass(className, instructorName, location));
        FitnessClass classAlreadyEnrolledIn = cs.findClassMemberEnrolledIn(new Member(fname,lname,dob));

        if (classAlreadyEnrolledIn == null || classAlreadyEnrolledIn.equals(classToEnrollIn)){
            return null;
        }

        if (classAlreadyEnrolledIn.getTime().equals(classToEnrollIn.getTime())){
            return ("Time conflict - " + classToEnrollIn.getClassName().name() + " - " + classToEnrollIn.getInstructor().name()
                    + ", " + classToEnrollIn.getTime().toString() +
                    ", " + classToEnrollIn.getLocation().toString());
        }
        return null;
    }

    /**
     * Prints out fitness class
     * Includes the class name, instructor name, time,
     * followed by all members and guests that have checked into the class
     */
    private String printFitnessClass(FitnessClass fitnessClass){
        String toReturn = "";
        toReturn = toReturn + (fitnessClass.toString()) + "\n";
        if (!fitnessClass.memberListIsEmpty()){
            toReturn = toReturn + ("- Participants - ") + "\n";
            int size = fitnessClass.getMemberListSize();
            for (int memberIndex = 0; memberIndex < size; memberIndex++){
                toReturn = toReturn + (fitnessClass.getIndexedMemberFromMemberList(memberIndex).toString()) + "\n";
            }
        }
        if (!fitnessClass.guestListIsEmpty()) {
            toReturn = toReturn + ("- Guests - ") + "\n";
            int size = fitnessClass.getGuestListSize();
            for (int guestIndex = 0; guestIndex < size; guestIndex++){
                toReturn = toReturn + (fitnessClass.getIndexedMemberFromGuestList(guestIndex).toString()) + "\n";
            }
        }
        return toReturn;
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