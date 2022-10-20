package fitnessmanager;

/**
 * Time Enum Class holds constant times when gym classes can occur
 * Stores the following time data:
 *  - hour
 *  - minute
 * @author Arya Shetty, John Greaney-Cheng
 */
public enum Time {
    MORNING(9, 30),
    AFTERNOON(14, 00),
    EVENING(18, 30);

    private final int hour;
    private final int minute;

    /**
     * Creates a Time constant object
     * @param hour, integer that references hour of class
     * @param minute, integer that references minute of class
     */
    Time(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Getter method for the time's hour value
     * @return hour value
     */
    public int getHour() {
        return hour;
    }

    /**
     * Getter method for the time's minute value
     * @return minute value
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Method takes in time name and returns a time enum
     * @param str string that represents time name
     * @return time string was referring to if it exists,
     *         null otherwise
     */
    public static Time stringToTime(String str){
        for (Time time: Time.values()){
            if (str.equalsIgnoreCase(time.name())){
                return time;
            }
        }
        return null;
    }
}
