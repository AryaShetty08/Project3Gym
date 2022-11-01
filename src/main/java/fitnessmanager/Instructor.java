package fitnessmanager;

/**
 * Instructor Enum Class holds all potential instructors of fitness classes
 * @author Arya Shetty, John Greaney-Cheng
 */
public enum Instructor {
    JENNIFER,
    KIM,
    DENISE,
    DAVIS,
    EMMA;

    /**
     * Method takes in instructor name and returns an instructor enum
     * @param str, string that represents instructor name
     * @return instructor string was referring to if they exist,
     *         null otherwise
     */
    public static Instructor stringToInstructor(String str){
        for (Instructor instructor: Instructor.values()){
            if (str.equalsIgnoreCase(instructor.name())){
                return instructor;
            }
        }
        return null;
    }
}
