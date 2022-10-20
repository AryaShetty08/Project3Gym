package fitnessmanager;

/**
 * Enum class that contains all fitness class types,
 * @author Arya Shetty, John Greaney-Cheng
 */
public enum ClassName {
    PILATES,
    SPINNING,
    CARDIO;

    /**
     * Method takes in class name and returns a ClassName enum
     * @param str, string that represents ClassName name
     * @return ClassName string was referring to if it exists,
     *         null otherwise
     */
    public static ClassName stringToClassName(String str){
        for (ClassName className: ClassName.values()){
            if (str.equalsIgnoreCase(className.name())){
                return className;
            }
        }
        return null;
    }
}
