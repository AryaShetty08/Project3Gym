package com.example.project3gym;

import fitnessmanager.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * The main controller class for the scene builder class
 * It converts all the functionality of the old gym manager class
 * into UI based output instead of terminal based
 * It still includes commands relating to gym member management,
 * such as Add Member, Remove Member, Check In Member and more
 * @author Arya Shetty, John Greaney-Cheng
 */
public class GymManagerController {
    private MemberDatabase db = new MemberDatabase();
    private ClassSchedule cs = new ClassSchedule();

    //Add FXML Variables
    @FXML
    private TextArea fNameAdd;
    @FXML
    private TextArea lNameAdd;
    @FXML
    private DatePicker dobAdd;
    @FXML
    private ToggleGroup membershipTypeAdd;
    @FXML
    private RadioButton standardToAdd;
    @FXML
    private RadioButton familyToAdd;
    @FXML
    private RadioButton premiumToAdd;
    @FXML
    private ToggleGroup locTypeAdd;
    @FXML
    private RadioButton locEdisonAdd;
    @FXML
    private RadioButton locFranklinAdd;
    @FXML
    private RadioButton locBridgewaterAdd;
    @FXML
    private RadioButton locPiscatawayAdd;
    @FXML
    private RadioButton locSomervilleAdd;
    @FXML
    private TextArea addOutput;

    //Remove FXML Variables
    @FXML
    private TextArea fNameRemove;
    @FXML
    private TextArea lNameRemove;
    @FXML
    private DatePicker dobRemove;
    @FXML
    private TextArea removeOutput;

    //Check FXML Variables
    @FXML
    private TextArea fNameCheck;
    @FXML
    private TextArea lNameCheck;
    @FXML
    private DatePicker dobCheck;
    @FXML
    private ToggleGroup locationTypeCheck;
    @FXML
    private RadioButton locEdisonCheck;
    @FXML
    private RadioButton locFranklinCheck;
    @FXML
    private RadioButton locBridgewaterCheck;
    @FXML
    private RadioButton locPiscatawayCheck;
    @FXML
    private RadioButton locSomervilleCheck;
    @FXML
    private ToggleGroup instructorCheck;
    @FXML
    private RadioButton instructorJenniferCheck;
    @FXML
    private RadioButton instructorDeniseCheck;
    @FXML
    private RadioButton instructorKimCheck;
    @FXML
    private RadioButton instructorDavisCheck;
    @FXML
    private RadioButton instructorEmmaCheck;
    @FXML
    private ToggleGroup classTypeCheck;
    @FXML
    private RadioButton cNamePilatesCheck;
    @FXML
    private RadioButton cNameSpinningCheck;
    @FXML
    private RadioButton cNameCardioCheck;
    @FXML
    private CheckBox guest;
    @FXML
    private TextArea checkOutput;

    //Print FXML Variables
    @FXML
    private RadioButton unsortedList;
    @FXML
    private RadioButton countyList;
    @FXML
    private RadioButton nameList;
    @FXML
    private RadioButton expireList;
    @FXML
    private RadioButton feeList;
    @FXML
    private RadioButton classSchedule;
    @FXML
    private TextArea printOutput;

    //Load FXML Variables
    @FXML
    private RadioButton loadMembers;
    @FXML
    private RadioButton loadClasses;
    @FXML
    private TextArea loadOutput;

    /**
     * A method that is run when the add button is clicked if conditions are met:
     * Helper method that checks if all fields are filled returns true,
     * and if the inputted data is all valid and matches with a member in a class
     * Finally it uses the add method in manager and outputs the result into the
     * text area on the register tab
     */
    @FXML
    protected void onAddButtonClick() {
        if(checkAddRequirements()) {
            MembershipType membershipType;
            if (standardToAdd.isSelected()) {
                membershipType = MembershipType.STANDARD;
            } else if (familyToAdd.isSelected()) {
                membershipType = MembershipType.FAMILY;
            } else if (premiumToAdd.isSelected()) {
                membershipType = MembershipType.PREMIUM;
            } else {
                membershipType = null;
            }
            Date dobTest = new Date(dobAdd.getValue().getMonthValue(), dobAdd.getValue().getDayOfMonth(), dobAdd.getValue().getYear());
            Location location;
            if (locEdisonAdd.isSelected()){
                location = Location.EDISON;
            } else if (locFranklinAdd.isSelected()){
                location = Location.FRANKLIN;
            } else if (locBridgewaterAdd.isSelected()){
                location = Location.BRIDGEWATER;
            } else if (locPiscatawayAdd.isSelected()){
                location = Location.PISCATAWAY;
            } else if (locSomervilleAdd.isSelected()){
                location = Location.SOMERVILLE;
            } else {
                location = null;
            }
            addOutput.setText(add(fNameAdd.getText(), lNameAdd.getText(), dobTest, location.name(), membershipType));
        }
    }

    /**
     * A method that is run when the remove button is clicked if conditions are met:
     * Helper method that checks if all fields are filled returns true,
     * and if the inputted data is all valid and matches with a member in a class
     * Finally it uses the remove method in manager and outputs the result into the
     * text area on the unenroll tab
     */
    @FXML
    protected void onRemoveButtonClick() {
        if(checkRemoveRequirements()) {
            Date dobTest = new Date(dobRemove.getValue().getMonthValue(), dobRemove.getValue().getDayOfMonth(), dobRemove.getValue().getYear());

            removeOutput.setText(remove(fNameRemove.getText(), lNameRemove.getText(), dobTest.toString()));
        }
    }

    /**
     * A method that is run when the check in button is clicked if conditions are met:
     * Helper method that checks if all fields are filled returns true,
     * and if the inputted data is all valid and matches with a member in a class
     * Finally if it's a guest it uses the guest check in method and outputs into the
     * text area on the check in/out tab, otherwise it outputs the normal check in result
     */
    @FXML
    protected void onCheckInButtonClick(){
        if (checkCheckRequirements()){
            Date dobTest = new Date(dobCheck.getValue().getMonthValue(), dobCheck.getValue().getDayOfMonth(), dobCheck.getValue().getYear());
            Location location = getCheckLocation();
            ClassName className = getCheckClassName();
            Instructor instructor = getCheckInstructor();
            if (!guest.isSelected()){
                checkOutput.setText(checkIn(className.name(), instructor.name(), location.name(), fNameCheck.getText(), lNameCheck.getText(), dobTest.toString()));
            }
            else {
                checkOutput.setText(checkInGuest(className.name(), instructor.name(), location.name(), fNameCheck.getText(), lNameCheck.getText(), dobTest.toString()));
            }
        }
    }

    /**
     * A method that is run when the checkout button is clicked if conditions are met:
     * Helper method that checks if all fields are filled returns true,
     * and if the inputted data is all valid and matches with a member in a class
     * Finally if it's a guest it uses the guest checkout method and outputs into the
     * text area on the check in/out tab, otherwise it outputs the normal checkout result
     */
    @FXML
    protected void onCheckOutButtonClick(){
        if (checkCheckRequirements()){
            Date dobTest = new Date(dobCheck.getValue().getMonthValue(), dobCheck.getValue().getDayOfMonth(), dobCheck.getValue().getYear());
            Location location = getCheckLocation();
            ClassName className = getCheckClassName();
            Instructor instructor = getCheckInstructor();
            if (!guest.isSelected()){
                checkOutput.setText(checkOut(className.name(), instructor.name(), location.name(), fNameCheck.getText(), lNameCheck.getText(), dobTest.toString()));
            }
            else {
                checkOutput.setText(checkOutGuest(className.name(), instructor.name(), location.name(), fNameCheck.getText(), lNameCheck.getText(), dobTest.toString()));
            }
        }
    }

    /**
     * A method that is run when the print button is clicked if conditions are met:
     * One of the radio buttons has to be selected otherwise nothing will be
     * outputted into the text area in the print tab
     * If one of the buttons is used the corresponding method in manager is used
     * to output the member list or class list in a certain order
     */
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
        else if(feeList.isSelected()){
            printOutput.setText(printMemberWithFees());
        }
        else if(classSchedule.isSelected()){
            printOutput.setText(printSchedule());
        }
    }

    /**
     * A method that runs when the load button is clicked
     * and one of the radio buttons is chosen, 'load members' or 'load classes'
     * It outputs a member list or class list from files in the program files
     * into the text area in the load tab
     */
    @FXML
    protected void onLoadButtonClick(){
        if(loadMembers.isSelected()){
            loadOutput.setText(db.loadMemberList());
        }
        else if(loadClasses.isSelected()){
            loadOutput.setText(cs.loadSchedule());
        }
    }

    /**
     * A method that runs when the quit tab is clicked
     * Instantly closes the application even if 'x' button isn't clicked
     */
    @FXML
    protected void onQuitButtonClick() {
        System.exit(0);
    }

    /**
     * A helper method of the add method in manager
     * Makes sure all fields are filled before implementing into member database
     * Conditions include:
     * - first name and last name is filled out
     * - a choice has been picked for date, membership type, and location
     * @return boolean, false if one of the fields has not been filled out
     *         and sends text to be outputted explaining which field has not been filled,
     *         true if everything has been filled out and can be implemented to the add method
     */
    private boolean checkAddRequirements() {
        if(fNameAdd.getText().isEmpty()) {
            addOutput.setText("Please enter your first name");
            return false;
        }
        else if(lNameAdd.getText().isEmpty()) {
            addOutput.setText("Please enter your last name");
            return false;
        }
        else if(dobAdd.getValue() == null) {
            addOutput.setText("Please enter in a valid date of birth");
            return false;
        }
        else if(membershipTypeAdd.getSelectedToggle() == null)
        {
            addOutput.setText("Please choose a membership type");
            return false;
        }
        else if(locTypeAdd.getSelectedToggle() == null) {
            addOutput.setText("Please choose a gym location");
            return false;
        }
        return true;
    }

    /**
     * Adds gym member to database
     * Checks if member can be added first
     * @param fname first name of the member to add
     * @param lname last name of the member to add
     * @param dob birthday of the member to add
     * @param location the gym the member belongs to
     * @param membershipType membership type of the gym member
     * @return String representation of member if added
     *         Explanation of why not added if member isn't added
     */
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

    /**
     * Checks if member meets all conditions to be added
     * Conditions include:
     * - Both birthday and expiration date of membership must be valid dates
     * - Member's birthday can't be today or future date
     * - Member must be over 18
     * - The gym location the member belongs to must exist
     * @param birthday birthday of the member
     * @param location the gym the member belongs to
     * @return String explanation of why member can't be added if they can't be added
     *         null if member can be added
     */
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

    /**
     * A helper method of the remove member method of the manager
     * Makes sure all fields are filled before trying to find the member in database
     * Conditions include:
     * - first name and last name is filled out
     * - a choice has been picked for date
     * @return boolean, false if one of the fields has not been filled out
     *         and sends text to be outputted explaining which field has not been filled,
     *         true if everything has been filled out and can be implemented to the remove method
     */
    private boolean checkRemoveRequirements() {
        if(fNameRemove.getText().isEmpty()) {
            removeOutput.setText("Please enter your first name");
            return false;
        }
        else if(lNameRemove.getText().isEmpty()) {
            removeOutput.setText("Please enter your last name");
            return false;
        }
        else if(dobRemove.getValue() == null) {
            removeOutput.setText("Please enter in a valid date of birth");
            return false;
        }
        return true;
    }

    /**
     * Removes gym member from database
     * Checks if gym member is in database,
     * if so removes them
     * @param fname first name of the member to remove
     * @param lname last name of the member to remove
     * @param dob birthday of the member to remove
     * @return String saying if member is removed
     */
    private String remove(String fname, String lname, String dob){
        if (this.db.remove(new Member(fname, lname, dob))){                                  //Checks if member exists in the database
            return (fname + " " + lname + " removed.");
        }
        else{
            return (fname + " " + lname + " is not in the database.");
        }
    }

    /**
     * A helper method of the check in/check out methods of manager
     * Makes sure all fields are filled before implementing into database of classes
     * Conditions include:
     * - first name and last name is filled out
     * - a choice has been picked for date, location, class, and instructor
     * @return boolean, false if one of the fields has not been filled out
     *         and sends text to be outputted explaining which field has not been filled,
     *         true if everything has been filled out and can be implemented to the check in/out method
     */
    private boolean checkCheckRequirements() {
        if(fNameCheck.getText().isEmpty()) {
            checkOutput.setText("Please enter your first name");
            return false;
        }
        else if(lNameCheck.getText().isEmpty()) {
            checkOutput.setText("Please enter your last name");
            return false;
        }
        else if(dobCheck.getValue() == null) {
            checkOutput.setText("Please enter in a valid date of birth");
            return false;
        }
        else if(locationTypeCheck.getSelectedToggle() == null) {
            checkOutput.setText("Please choose a gym location");
            return false;
        }
        else if(classTypeCheck.getSelectedToggle() == null) {
            checkOutput.setText("Please choose a class type (e.g. Pilates)");
            return false;
        }
        else if(instructorCheck.getSelectedToggle() == null) {
            checkOutput.setText("Please choose an instructor");
            return false;
        }
        return true;
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
     * @return String saying member checked in if member checks in
     *         Explanation of why not checked in if member doesn't check in
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
     * @return String saying if member dropped the class
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
     * @return String saying guest checked in if guest checks in
     *         Explanation of why not checked in if guest doesn't check in
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
     * @return String saying if guest dropped the class
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
     * @return null if member and class meet conditions
     *         String describing invalid condition otherwise
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
     * @return String describing time conflict if time conflict exists
     *         null otherwise
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
     * Returns unsorted list of members
     * Checks if list is empty,
     * if not, returns unsorted list of members
     * @return unsorted list of members if nonempty
     *         statement that member database is empty if empty
     */
    private String printMemberList(){
        String memberList = "";
        if (this.db.isEmpty()){
            return "Member Database is empty!" + "\n";
        }
        memberList = memberList + "-list of members-" + "\n";
        memberList = memberList + this.db.print() + "\n";
        memberList = memberList + "-end of list-" + "\n";
        return memberList;
    }

    /**
     * Returns sorted list of members ordered by county their gym is in (alphabetically),
     * Sorts can be based on
     * - county and zip code
     * - membership expiration date
     * - name of members
     * First checks if list is empty,
     * if not, returns list sorted by input parameter
     * @param category the category the members are sorted by
     * @return sorted list of members if nonempty
     *         statement that member database is empty if empty
     */
    private String printSortedMemberList(SortCategory category){
        if (this.db.isEmpty()){
            return ("Member Database is empty!") + "\n";
        }
        String memberList = "";
        switch (category){
            case COUNTY:
                memberList = memberList + ("-list of members sorted by county and zipcode-") + "\n";
                memberList = memberList + this.db.sortedPrint(SortCategory.COUNTY);
                break;
            case NAME:
                memberList = memberList + ("-list of members sorted by last name, and first name-") + "\n";
                memberList = memberList + this.db.sortedPrint(SortCategory.NAME);
                break;
            case EXPIRATION_DATE:
                memberList = memberList + ("-list of members sorted by membership expiration date-") + "\n";
                memberList = memberList + this.db.sortedPrint(SortCategory.EXPIRATION_DATE);
                break;
        }
        memberList = memberList + ("-end of list-") + "\n";
        return memberList;
    }

    /**
     * Returns unsorted list of members with their fees and guest passes
     * Checks if list is empty,
     * if not, returns unsorted list of members with fees
     * @return unsorted list of members with their fees and guest passes if nonempty
     *         statement that member database is empty if empty
     */
    private String printMemberWithFees(){
        if (this.db.isEmpty()){
            return ("Member Database is empty!") + "\n";
        }
        String memberList = "";
        memberList = memberList + ("-list of members with membership fees-") + "\n";
        memberList = memberList + this.db.printWithFees();
        memberList = memberList + ("-end of list-") + "\n";
        return memberList;
    }

    /**
     * Returns the fitness class schedule
     * Checks if class schedule is empty
     * If not, returns class schedule
     * @return fitness class schedule if nonempty
     *         statement that class schedule is empty if empty
     */
    private String printSchedule(){
        String classList = "";
        if (cs.isEmpty()){
            return ("Fitness class schedule is empty") + "\n";
        }
        classList = classList + ("-Fitness classes-") + "\n";
        classList = classList + cs.printClasses();
        classList = classList + ("-end of class list.") + "\n";
        return classList;
    }

    /**
     * Returns String representation of fitness class
     * Includes the class name, instructor name, time,
     * followed by all members and guests that have checked into the class
     * @return String representation of fitness class
     */
    private String printFitnessClass(FitnessClass fitnessClass){
        String fcRepresentation = "";
        fcRepresentation = fcRepresentation + (fitnessClass.toString()) + "\n";
        if (!fitnessClass.memberListIsEmpty()){
            fcRepresentation = fcRepresentation + ("- Participants - ") + "\n";
            int size = fitnessClass.getMemberListSize();
            for (int memberIndex = 0; memberIndex < size; memberIndex++){
                fcRepresentation = fcRepresentation + (fitnessClass.getIndexedMemberFromMemberList(memberIndex).toString()) + "\n";
            }
        }
        if (!fitnessClass.guestListIsEmpty()) {
            fcRepresentation = fcRepresentation + ("- Guests - ") + "\n";
            int size = fitnessClass.getGuestListSize();
            for (int guestIndex = 0; guestIndex < size; guestIndex++){
                fcRepresentation = fcRepresentation + (fitnessClass.getIndexedMemberFromGuestList(guestIndex).toString()) + "\n";
            }
        }
        return fcRepresentation;
    }

    /**
     * Returns expiration date of newly added member
     * Expiration date depends on membership type of member
     * For Standard and Family, expiration date is three months
     * from today
     * For Premium, expiration date is one year from today
     * @param membershipType membership type of member
     * @return expiration date corresponding to membership type
     */
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

    /**
     * Checks if member is over 18 years old
     * Compares birth year and current year to see if
     * difference is greater than or less than 18. If difference
     * is equal to 18, compares months and if months are equal,
     * compares days.
     * @param birthday birthday of the member
     * @param today current day's date
     * @return true if member is 18 or older, false otherwise
     */
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

    /**
     * Helper method that returns location for Check In/Check Out
     * @return location for Check In/Check Out
     */
    private Location getCheckLocation(){
        Location location;
        if (locEdisonCheck.isSelected()){
            location = Location.EDISON;
        } else if (locFranklinCheck.isSelected()){
            location = Location.FRANKLIN;
        } else if (locBridgewaterCheck.isSelected()){
            location = Location.BRIDGEWATER;
        } else if (locPiscatawayCheck.isSelected()){
            location = Location.PISCATAWAY;
        } else if (locSomervilleCheck.isSelected()){
            location = Location.SOMERVILLE;
        } else {
            location = null;
        }
        return location;
    }

    /**
     * Helper method that returns class type for Check In/Check Out
     * @return class type for Check In/Check Out
     */
    private ClassName getCheckClassName(){
        ClassName className;
        if (cNamePilatesCheck.isSelected()){
            className = ClassName.PILATES;
        } else if (cNameSpinningCheck.isSelected()){
            className = ClassName.SPINNING;
        } else if (cNameCardioCheck.isSelected()){
            className = ClassName.CARDIO;
        } else {
            className = null;
        }
        return className;
    }

    /**
     * Helper method that returns instructor for Check In/Check Out
     * @return instructor for Check In/Check Out
     */
    private Instructor getCheckInstructor(){
        Instructor instructor;
        if (instructorJenniferCheck.isSelected()){
            instructor = Instructor.JENNIFER;
        } else if (instructorDeniseCheck.isSelected()){
            instructor = Instructor.DENISE;
        } else if (instructorKimCheck.isSelected()){
            instructor = Instructor.KIM;
        } else if (instructorDavisCheck.isSelected()){
            instructor = Instructor.DAVIS;
        } else if (instructorEmmaCheck.isSelected()){
            instructor = Instructor.EMMA;
        } else {
            instructor = null;
        }
        return instructor;
    }
}