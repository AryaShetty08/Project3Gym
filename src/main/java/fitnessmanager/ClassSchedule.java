package fitnessmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * ClassSchedule is a class to store and sort fitness classes
 * Stores the following data:
 *  - FitnessClass list
 *  - Size of list
 * @author Arya Shetty, John Greaney-Cheng
 */
public class ClassSchedule {
    private FitnessClass[] classes;
    private int numClasses;

    /**
     * Creates a Class Schedule instance
     * Array starting capacity is 4
     */
    public ClassSchedule(){
        this.numClasses = 0;
        this.classes = new FitnessClass[Constant.STARTING_CAPACITY.getValue()];
    }

    /**
     * Adds a class into the schedule
     * If the array is at full capacity, grow list by 4
     * @param fc the class to add into the schedule
     */
    public void addClass(FitnessClass fc){
        classes[numClasses] = fc;
        numClasses++;
        if(numClasses == classes.length){
            grow();
        }
    }

    /**
     * Loads the fitness class schedule
     * Imports classes from "classSchedule.txt"
     * @return String representation of list of classes uploaded if uploaded
     *         statement that file isn't found if file isn't found
     */
    public String loadSchedule(){
        String classListString = "";
        try {
            File classSchedule = new File(".\\classSchedule.txt");
            Scanner sc = new Scanner(classSchedule);
            classListString = classListString + ("-Fitness classes loaded-") + "\n";
            while (sc.hasNext()){
                FitnessClass fc = new FitnessClass(sc.next(), sc.next(), sc.next(), sc.next());
                this.addClass(fc);
            }
            classListString = classListString + printClasses();
            classListString = classListString + ("-end of class list.") + "\n";
            sc.close();
        }
        catch(FileNotFoundException e) {
            classListString = classListString + ("File Not Found!") + "\n";
        }
        finally {
            return classListString;
        }
    }

    /**
     * Increases the size of the schedule list by four to store new classes
     * Creates a new FitnessClass array with the increased capacity
     * then stores the old classes in the list, and replaces the
     * old database list with the new one
     */
    private void grow(){
        int newLength = Constant.INCREASE_CAPACITY.getValue() + this.numClasses;
        FitnessClass[] newList = new FitnessClass[newLength];
        for (int i = 0; i < this.classes.length; i++) {         //Enters old class info into new larger list
            newList[i] = this.classes[i];
        }
        this.classes = newList;
    }

    /**
     * Returns fitness class in class schedule equal to input parameter
     * @param fc the fitness class to find in class schedule
     * @return equivalent fitness class in class schedule if it exists
     *         null otherwise
     */
    public FitnessClass classExists(FitnessClass fc){
        for(int i = 0; i < numClasses; i++){
            if(fc.equals(classes[i])){
                return classes[i];
            }
        }
        return null;
    }

    /**
     * Returns fitness class in class schedule containing input member
     * @param member member to find enrolled in fitness class in class schedule
     * @return fitness class containing member in class schedule if it exists
     *         null otherwise
     */
    public FitnessClass findClassMemberEnrolledIn(Member member){
        for(int i = 0; i < numClasses; i++){
            if(classes[i].find(member) != null){
                return classes[i];
            }
        }
        return null;
    }

    /**
     * Returns schedule of classes for the day the gym is run
     * Includes the class name, instructor name, time,
     * followed by all members and guests that have checked into the class
     * @return schedule of classes for the day the gym is run
     */
    public String printClasses(){
        String toReturn = "";
        for(int classIndex = 0; classIndex < numClasses; classIndex++){
            toReturn = toReturn + classes[classIndex].toString() + "\n";
            if (!classes[classIndex].memberListIsEmpty()){
                toReturn = toReturn + "- Participants - " + "\n";
                int size = classes[classIndex].getMemberListSize();
                for (int memberIndex = 0; memberIndex < size; memberIndex++){
                    toReturn = toReturn + classes[classIndex].getIndexedMemberFromMemberList(memberIndex).toString() + "\n";
                }
            }
            if (!classes[classIndex].guestListIsEmpty()) {
                toReturn = toReturn + "- Guests - " + "\n";
                int size = classes[classIndex].getGuestListSize();
                for (int guestIndex = 0; guestIndex < size; guestIndex++){
                    toReturn = toReturn + classes[classIndex].getIndexedMemberFromGuestList(guestIndex).toString() + "\n";
                }
            }

        }
        return toReturn;
    }

    /**
     * Checks if this schedule is empty
     * @return true if this schedule is empty, false otherwise
     */
    public boolean isEmpty(){
        return (this.numClasses == 0);
    }
}
