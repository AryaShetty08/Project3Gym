package fitnessmanager;

import java.util.ArrayList;

/**
 * FitnessClass Class is a blueprint for FitnessClass objects
 * Stores member databases for class as well as information
 * pertaining to class:
 *  - Type of Class
 *  - Instructor Name
 *  - Time it's held
 *  - Location
 * Contains separate arraylists for storing members and guests
 * Includes methods to check in and drop classes
 * @author Arya Shetty, John Greaney-Cheng
 */
public class FitnessClass{
    private ClassName className;
    private Instructor instructor;
    private Time time;
    private Location location;

    private ArrayList<Member> memberList;
    private ArrayList<Member> guestList;

    /**
     * Creates a FitnessClass instance
     * @param className type of class (e.g. Pilates)
     * @param instructor name of the class' instructor
     * @param time the time of the class
     * @param location the location of the class
     */
    public FitnessClass(String className, String instructor, String time, String location){
        this.memberList = new ArrayList<>();
        this.guestList = new ArrayList<>();
        this.className = ClassName.stringToClassName(className);
        this.instructor = Instructor.stringToInstructor(instructor);
        this.time = Time.stringToTime(time);
        this.location = Location.stringToLocation(location);
    }

    /**
     * Creates a FitnessClass instance
     * @param className type of class (e.g. Pilates)
     * @param instructor name of the class' instructor
     * @param location the location of the class
     */
    public FitnessClass(String className, String instructor, String location){
        this.memberList = new ArrayList<>();
        this.guestList = new ArrayList<>();
        this.className = ClassName.stringToClassName(className);
        this.instructor = Instructor.stringToInstructor(instructor);
        this.location = Location.stringToLocation(location);
    }

    /**
     * Returns String representation of this fitness class
     * Format ex: PILATES - JENNIFER, 9:30, BRIDGEWATER
     * @return String representation of this fitness class
     */
    @Override
    public String toString(){
        if (time.getMinute() == 0){
            return (className.name() + " - " + instructor.name() + ", " + time.getHour()
                    + ":" + "00, " + location.name());
        }
        else {
            return (className.name() + " - " + instructor.name() + ", " + time.getHour()
                    + ":" + time.getMinute() + ", " + location.name());
        }
    }

    /**
     * Checks member into this class
     * First verifies that member isn't already checked into this class
     * @param member member to check into this cla
     * @return true if member was checked into this class
     *         false otherwise
     */
    public boolean checkIn(Member member) {
        if (this.memberList.contains(member)){
            return false;
        }
        this.memberList.add(member);
        return true;
    }

    /**
     * Drops member from this class
     * @param member member to drop from this class
     * @return true if member was dropped from this class
     *         false otherwise
     */
    public boolean checkOut(Member member){
        return this.memberList.remove(member);
    }

    /**
     * Checks guest into this class
     * @param member member with guest to check into this class
     */
    public void checkInGuest(Member member){
        this.guestList.add(member);
    }

    /**
     * Drops guest from this class
     * @param member member with guest to drop from this class
     * @return true if guest was dropped from this class
     *         false otherwise
     */
    public boolean checkOutGuest(Member member){
        return this.guestList.remove(member);
    }


    /**
     * Tries to find member in member list equal to input parameter
     * Goes through list to see if any stored member is
     * equal to parameter member
     * @param memberToFind the member to find in member list
     * @return the member stored in the list that's equal to
     *              the input parameter
     *         null if there is no such member in the database
     */
    public Member find(Member memberToFind) {
        for (Member memberCheckedIn: memberList){
            if (memberToFind.equals(memberCheckedIn)){
                return memberCheckedIn;
            }
        }
        return null;
    }

    /**
     * Tries to find member in guest list equal to input parameter
     * Goes through list to see if any stored member that checked in
     * a guest is equal to parameter member
     * @param memberToFind the member to find in guest list
     * @return the member stored in the list that's equal to
     *              the input parameter
     *         null if there is no such member in the database
     */
    public Member findGuest(Member memberToFind) {
        for (Member memberWithGuestCheckedIn: guestList){
            if (memberToFind.equals(memberWithGuestCheckedIn)){
                return memberWithGuestCheckedIn;
            }
        }
        return null;
    }

    /**
     * Determine equality between this fitness class and another
     * Two fitness classes are equal if
     *  - They have the same instructor
     *  - They have the same class type
     *  - They have the same location
     * @param obj the fitness class to check equality with
     * @return true if fitness classes are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof FitnessClass){
            FitnessClass fc = (FitnessClass) obj;
            return (fc.getInstructor().equals(this.getInstructor())
                    && fc.getClassName().equals(this.getClassName())
                    && fc.getLocation().equals(this.getLocation()));
        }
        return false;
    }

    /**
     * Getter method for class's type (ex: Pilates)
     * @return class's type
     */
    public ClassName getClassName(){
        return this.className;
    }

    /**
     * Getter method for class's instructor
     * @return class's instructor
     */
    public Instructor getInstructor(){
        return this.instructor;
    }

    /**
     * Getter method for class's time
     * @return class's time
     */
    public Time getTime(){
        return this.time;
    }

    /**
     * Getter method for class's location
     * @return class's location
     */
    public Location getLocation(){
        return this.location;
    }

    /**
     * Checks if member list is empty
     * @return true if guest list is empty, false otherwise
     */
    public boolean memberListIsEmpty(){
        return this.memberList.isEmpty();
    }

    /**
     * Checks if guest list is empty
     * @return true if guest list is empty, false otherwise
     */
    public boolean guestListIsEmpty(){
        return this.guestList.isEmpty();
    }

    /**
     * Getter method for member list size
     * @return member list size
     */
    public int getMemberListSize() {
        return memberList.size();
    }

    /**
     * Getter method for guest list size
     * @return guest list size
     */
    public int getGuestListSize() {
        return guestList.size();
    }

    /**
     * Getter method for specific index of member list
     * @param index index whose member in member list is to be returned
     * @return member from member list corresponding to index
     */
    public Member getIndexedMemberFromMemberList(int index){
        return memberList.get(index);
    }

    /**
     * Getter method for specific index of guest list
     * @param index index whose member in guest list is to be returned
     * @return member from guest list corresponding to index
     */
    public Member getIndexedMemberFromGuestList(int index){
        return guestList.get(index);
    }
}



