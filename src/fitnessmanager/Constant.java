package fitnessmanager;

/**
 * Constant Enum Class holds all variables with constant values
 * Stores the value corresponding to each constant
 * @author Arya Shetty, John Greaney-Cheng
 */
public enum Constant {
    QUADRENNIAL(4),
    CENTENNIAL(100),
    QUARTERCENTENNIAL(400),
    LEAP_DAY(29),
    FIRST_DAY_OF_MONTH(1),
    MINIMUM_AGE(18),
    STARTING_CAPACITY(4),
    INCREASE_CAPACITY(4),
    QUARTERLY_NUM_MONTHS(3),
    ANNUALLY_NUM_MONTHS(12),
    FAMILY_MAX_PASS_NUM(1),
    PREMIUM_MAX_PASS_NUM(3);

    private final int value;

    /**
     * Creates a Constant
     * @param value the corresponding value of the constant
     */
    Constant(int value){
        this.value = value;
    }

    /**
     * Getter method for value of constant
     * @return constant's value
     */
    public int getValue(){
        return this.value;
    }
}
