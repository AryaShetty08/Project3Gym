package fitnessmanager;

/**
 * Month Enum Class holds all months
 * Stores the following data:
 *  - Month's expression as a number
 *  - Last day in month (28 for February)
 * @author Arya Shetty, John Greaney-Cheng
 */
public enum Month {
    JANUARY(1, 31),
    FEBRUARY(2, 28),
    MARCH(3, 31),
    APRIL(4, 30),
    MAY(5, 31),
    JUNE(6, 30),
    JULY(7, 31),
    AUGUST(8, 31),
    SEPTEMBER(9, 30),
    OCTOBER(10, 31),
    NOVEMBER(11, 30),
    DECEMBER(12, 31);

    private final int numberMonth;
    private final int lastDayInMonth;

    /**
     * Creates a Month constant object
     * @param numberMonth, the month's expression as a number
     * @param lastDayInMonth, the last day in the month (28 for February)
     */
    Month(int numberMonth, int lastDayInMonth){
        this.numberMonth = numberMonth;
        this.lastDayInMonth = lastDayInMonth;
    }

    /**
     * Getter method for numerical representation of month
     * @return numerical representation of month
     */
    public int getNumberMonth(){
        return this.numberMonth;
    }

    /**
     * Getter method for last day in month
     * @return last day in month
     */
    public int getLastDayInMonth(){
        return this.lastDayInMonth;
    }

    /**
     * Method takes in int and returns corresponding month enum
     * @param numberMonth, int that represents month
     *   (e.g. 1 - January, 2 - February...)
     * @return month that corresponds to numberMonth
     *         null otherwise
     */
    public static Month intToMonth(int numberMonth){
        for (Month month: Month.values()){
            if (numberMonth == month.getNumberMonth()){
                return month;
            }
        }
        return null;
    }
}
