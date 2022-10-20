package fitnessmanager;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

/**
 * User Interface class that processes commands entered into the console
 * Focuses on commands relating to gym member management,
 * such as Add Member, Remove Member, Check In Member
 * @author Arya Shetty, John Greaney-Cheng
 */
public class GymManager {
    private MemberDatabase db;                          //Member Database used to keep track of members in gym
    private ClassSchedule cs;

    /**
     * Creates an instance of the GymManager Class
     */
    public GymManager(){
        this.db = new MemberDatabase();
        this.cs = new ClassSchedule();
    }

    /**
     * Continually processes user inputted commands
     * Terminates when user enters command to quit
     * Commands are checked with two helper methods:
     * Old (Same Functionality as Project 1)
     * New (New Project 2 Functionality)
     * Checks old commands first, if old command isn't run,
     * then checks new commands
     * Note: Invalid Command and Quit are in new helper method
     */
    public void run(){
        System.out.println("Gym Manager Running...");
        Scanner sc = new Scanner(System.in);
        boolean qValue = false;
        while (!qValue){                               //Checks if user has given command to quit
            String command = sc.next();
            if (!checkCommandOld(sc, command)){
                qValue = checkCommandNew(sc, command);
            }
        }
        sc.close();
        System.out.println("Gym Manager terminated.");
    }

    /**
     * Checks and runs if command is from Project 1
     * @param sc scanner to receive user input
     * @param command the command to check and run
     * @return true if a command was run, false otherwise
     */
    private boolean checkCommandOld(Scanner sc, String command){
        switch (command){
            case "R":                                   //Remove member from database
                remove(sc.next(), sc.next(), sc.next());
                return true;
            case "P":                                   //Print unsorted list of members from database
                printMemberList();
                return true;
            case "PC":                                  //Print list of members from database sorted by county
                printSortedMemberList(SortCategory.COUNTY);
                return true;
            case "PN":                                  //Print list of members from database sorted by name
                printSortedMemberList(SortCategory.NAME);
                return true;
            case "PD":                                  //Print list of members from database sorted by expiration date
                printSortedMemberList(SortCategory.EXPIRATION_DATE);
                return true;
            case "S":                                   //Print fitness class schedule
                printSchedule();
                return true;
        }
        return false;
    }

    /**
     * Checks and runs if command is from (or updated for) Project 2
     * @param sc scanner to receive user input
     * @param command the command to check and run
     * @return true if command was given to terminate, false otherwise
     */
    private boolean checkCommandNew(Scanner sc, String command){
        switch (command){
            case "A":                                   //Add member to database
                add(sc.next(), sc.next(), sc.next(), sc.next(), MembershipType.STANDARD);
                break;
            case "LS":
                loadSchedule();
                break;
            case "LM":
                loadMemberList();
                break;
            case "AF":
                add(sc.next(), sc.next(), sc.next(), sc.next(), MembershipType.FAMILY);
                break;
            case "AP":
                add(sc.next(), sc.next(), sc.next(), sc.next(), MembershipType.PREMIUM);
                break;
            case "PF":
                printMemberWithFees();
                break;
            case "C":                                   //Check in member to a fitness class
                checkIn(sc.next(), sc.next(), sc.next(), sc.next(), sc.next(), sc.next());
                break;
            case "CG":
                checkInGuest(sc.next(), sc.next(), sc.next(), sc.next(), sc.next(), sc.next());
                break;
            case "D":                                   //Drops member from fitness class
                checkOut(sc.next(), sc.next(), sc.next(), sc.next(), sc.next(), sc.next());
                break;
            case "DG":
                checkOutGuest(sc.next(), sc.next(), sc.next(), sc.next(), sc.next(), sc.next());
                break;
            case "Q":                                   //Quit user interface
                return true;
            default:                                    //Otherwise, command is invalid
                invalidCommand(command);
        }
        return false;
    }

    /**
     * Adds gym member to database
     * Checks if member can be added first
     * @param fname first name of the member to add
     * @param lname last name of the member to add
     * @param dob birthday of the member to add
     * @param location the gym the member belongs to
     * @param membershipType membership type of the gym member
     */
    private void add(String fname, String lname, String dob, String location, MembershipType membershipType){
        if (canBeAdded(dob, location)){
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
                System.out.println(fname + " " + lname + " added.");
            }
            else {
                System.out.println(fname + " " + lname + " is already in the database.");
            }
        }
    }

    /**
     * Checks if member meets all conditions to be added
     * Conditions include:
     * - Both birthday and expiration date of membership must be valid dates
     * - Member's birthday can't be today or future date
     * - Member must be over 18
     * - The gym location the member belongs to must exist
     * @param dob birthday of the member
     * @param location the gym the member belongs to
     * @return true if member meets conditions to be added, false otherwise
     */
    private boolean canBeAdded(String dob, String location){
        Date birthday = new Date(dob);
        if (!birthday.isValid()){                                                       //Checks if birthday date is a valid date
            System.out.println("DOB " + dob + ": invalid calendar date!");
            return false;
        }
        Date today = new Date();
        if (birthday.compareTo(today) >= 0){                                            //Checks if birthday date is before today
            System.out.println("DOB " + dob + ": cannot be today or a future date!");
            return false;
        }
        if (!isOverEighteen(birthday, today)){                                          //Checks if member is over 18
            System.out.println("DOB " + dob + ": must be 18 or older to join!");
            return false;
        }
        if (Location.stringToLocation(location) == null){                                   //Checks if member's gym location exists
            System.out.println(location + ": invalid location!");
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
     */
    private void remove(String fname, String lname, String dob){
        if (this.db.remove(new Member(fname, lname, dob))){                                  //Checks if member exists in the database
            System.out.println(fname + " " + lname + " removed.");
        }
        else{
            System.out.println(fname + " " + lname + " is not in the database.");
        }
    }

    /**
     * Prints out unsorted list of members
     * Checks if list is empty,
     * if not, prints unsorted list of members
     */
    private void printMemberList(){
        if (this.db.isEmpty()){
            System.out.println("Member Database is empty!");
            return;
        }
        System.out.println("-list of members-");
        this.db.print();
        System.out.println("-end of list-");
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
    private void printSortedMemberList(SortCategory category){
        if (this.db.isEmpty()){
            System.out.println("Member Database is empty!");
            return;
        }
        switch (category){
            case COUNTY:
                System.out.println("-list of members sorted by county and zipcode-");
                this.db.sortedPrint(SortCategory.COUNTY);
                break;
            case NAME:
                System.out.println("-list of members sorted by last name, and first name-");
                this.db.sortedPrint(SortCategory.NAME);
                break;
            case EXPIRATION_DATE:
                System.out.println("-list of members sorted by membership expiration date-");
                this.db.sortedPrint(SortCategory.EXPIRATION_DATE);
                break;
        }
        System.out.println("-end of list-");
    }

    /**
     * Prints out unsorted list of members with their fees and guest passes
     * Checks if list is empty,
     * if not, prints unsorted list of members with fees
     */
    private void printMemberWithFees(){
        if (this.db.isEmpty()){
            System.out.println("Member Database is empty!");
            return;
        }
        System.out.println("-list of members with membership fees-");
        this.db.printWithFees();
        System.out.println("-end of list-");
    }

    /**
     * Prints the fitness class schedule
     * Checks if class schedule is empty
     * If not, prints class schedule
     */
    private void printSchedule(){
        if (cs.isEmpty()){
            System.out.println("Fitness class schedule is empty");
            return;
        }
        System.out.println("-Fitness classes-");
        cs.printClasses();
        System.out.println("-end of class list.");
    }

    /**
     * Prints out fitness class
     * Includes the class name, instructor name, time,
     * followed by all members and guests that have checked into the class
     */
    private void printFitnessClass(FitnessClass fitnessClass){
        System.out.println(fitnessClass.toString());
        if (!fitnessClass.memberListIsEmpty()){
            System.out.println("- Participants - ");
            int size = fitnessClass.getMemberListSize();
            for (int memberIndex = 0; memberIndex < size; memberIndex++){
                System.out.println(fitnessClass.getIndexedMemberFromMemberList(memberIndex).toString());
            }
        }
        if (!fitnessClass.guestListIsEmpty()) {
            System.out.println("- Guests - ");
            int size = fitnessClass.getGuestListSize();
            for (int guestIndex = 0; guestIndex < size; guestIndex++){
                System.out.println(fitnessClass.getIndexedMemberFromGuestList(guestIndex).toString());
            }
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
    private void checkIn(String className, String instructorName, String location, String fname, String lname, String dob){
        if (validFitnessClassConditions(className, instructorName, location, fname, lname, dob)
                && !classConflict(className, instructorName, location, fname, lname, dob)){
            Member memberToCheckIn = this.db.findMember(new Member(fname, lname, dob));
            if (!(memberToCheckIn instanceof Family)){
                if (!memberToCheckIn.getLocation().equals(Location.stringToLocation(location))){
                    Location restrictedLocation = Location.stringToLocation(location);
                    System.out.println(fname + " " + lname + " checking in " + restrictedLocation.toString()
                            + " - standard membership location restriction.");
                    return;
                }
            }
            FitnessClass classToEnrollIn = this.cs.classExists(new FitnessClass(className, instructorName, location));
            if (classToEnrollIn.checkIn(memberToCheckIn)){
                System.out.print(fname + " " + lname + " checked in ");
                printFitnessClass(classToEnrollIn);
            }
            else {
                System.out.println(fname + " " + lname + " already checked in.");
            }
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
    private void checkOut(String className, String instructorName, String location, String fname, String lname, String dob) {
       if(validFitnessClassConditions(className, instructorName, location, fname, lname, dob)){
           FitnessClass classToDrop = cs.classExists(new FitnessClass(className, instructorName, location));
           Member memberToDrop = this.db.findMember(new Member(fname, lname, dob));
           if(classToDrop.find(memberToDrop) != null){
               classToDrop.checkOut(memberToDrop);
               System.out.println(fname + " " + lname + " done with the class.");
               return;
           }
           System.out.println(fname + " " + lname + " did not check in.");
       }
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
    private void checkInGuest(String className, String instructorName, String location, String fname, String lname, String dob){
        if(validFitnessClassConditions(className, instructorName, location, fname, lname, dob)){
            Member memberWithGuest = this.db.findMember(new Member(fname, lname, dob));

            if(!(memberWithGuest instanceof Family)){
                System.out.println("Standard membership - guest check-in is not allowed.");
                return;
            }

            if (!Location.stringToLocation(location).equals(memberWithGuest.getLocation())){
                System.out.println(fname + " " + lname + " Guest checking in " + Location.stringToLocation(location).toString()
                        + " - guest location restriction.");
                return;
            }

            boolean havePass = false;
            if(memberWithGuest instanceof Premium){
                havePass = ((Premium) memberWithGuest).useGuestPass();
            }
            else if(memberWithGuest instanceof Family){
                havePass = ((Family) memberWithGuest).useGuestPass();
            }
            if (!havePass){
                System.out.println(fname + " " + lname + " ran out of guest pass.");
                return;
            }
            FitnessClass classToEnrollIn = this.cs.classExists(new FitnessClass(className, instructorName, location));
            classToEnrollIn.checkInGuest(memberWithGuest);
            System.out.print(fname + " " + lname + " (guest) checked in ");
            printFitnessClass(classToEnrollIn);
        }
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
    private void checkOutGuest(String className, String instructorName, String location, String fname, String lname, String dob){
        if(validFitnessClassConditions(className, instructorName, location, fname, lname, dob)){
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
                System.out.println(fname + " " + lname + " Guest done with the class.");
                return;
            }
            System.out.println(fname + " " + lname + " Guest did not check in.");
        }
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
    private boolean validFitnessClassConditions(String className, String instructorName, String location, String fname, String lname, String dob) {
        Date birthday = new Date(dob);
        if (!birthday.isValid()) {                                                       //Checks if birthday date is a valid date
            System.out.println("DOB " + dob + ": invalid calendar date!");
            return false;
        }

        if (Instructor.stringToInstructor(instructorName) == null) {
            System.out.println(instructorName + " - instructor does not exist.");
            return false;
        }

        Member memberToCheckIn = this.db.findMember(new Member(fname, lname, dob));
        if (memberToCheckIn == null) {
            System.out.println(fname + " " + lname + " " + dob + " is not in the database.");
            return false;
        }

        if (ClassName.stringToClassName(className) == null) {
            System.out.println(className + " class does not exist.");
            return false;
        }

        if (memberToCheckIn.getExpire().compareTo(new Date()) <= 0) {
            System.out.println(fname + " " + lname + " " + dob + " membership expired.");
            return false;
        }

        if (Location.stringToLocation(location) == null) {
            System.out.println(location + " - invalid location.");
            return false;
        }

        if (cs.classExists(new FitnessClass(className, instructorName, location)) == null) {
            System.out.println(className + " by " + instructorName + " does not exist at " + location);
            return false;
        }
        return true;
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
    private boolean classConflict(String className, String instructorName, String location, String fname, String lname, String dob){
        FitnessClass classToEnrollIn = cs.classExists(new FitnessClass(className, instructorName, location));
        FitnessClass classAlreadyEnrolledIn = cs.findClassMemberEnrolledIn(new Member(fname,lname,dob));

        if (classAlreadyEnrolledIn == null || classAlreadyEnrolledIn.equals(classToEnrollIn)){
            return false;
        }

        if (classAlreadyEnrolledIn.getTime().equals(classToEnrollIn.getTime())){
            System.out.println("Time conflict - " + classToEnrollIn.getClassName().name() + " - " + classToEnrollIn.getInstructor().name()
                    + ", " + classToEnrollIn.getTime().toString() +
                    ", " + classToEnrollIn.getLocation().toString());
            return true;
        }
        return false;
    }

    /**
     * Loads the fitness class schedule
     * Imports classes from "classSchedule.txt"
     */
    private void loadSchedule(){
        try {
            File classSchedule = new File(".\\classSchedule.txt");
            Scanner sc = new Scanner(classSchedule);
            System.out.println("-Fitness classes loaded-");
            while (sc.hasNext()){
                FitnessClass fc = new FitnessClass(sc.next(), sc.next(), sc.next(), sc.next());
                this.cs.addClass(fc);
            }
            cs.printClasses();
            System.out.println("-end of class list.");
            sc.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("File Not Found!");
        }
    }

    /**
     * Loads a list of members to the database
     * Imports members from "memberList.txt"
     */
    private void loadMemberList(){
        try {
            File memberList = new File(".\\memberList.txt");
            Scanner sc = new Scanner(memberList);
            System.out.println("-list of members loaded-");
            while (sc.hasNext()){
                Member toAdd = new Member(sc.next(), sc.next(), sc.next(), sc.next(), sc.next());
                db.add(toAdd);
                System.out.println(toAdd.toString());
            }
            System.out.println("-end of list-");
            sc.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("File Not Found!");
        }

    }

    /**
     * Prints output for invalid user command
     * @param command the invalid user input
     */
    private void invalidCommand(String command){
        System.out.println(command + " is an invalid command!");
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

}