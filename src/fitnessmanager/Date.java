package fitnessmanager;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Date Class is a blueprint for date objects
 * Stores the month, day, and year of date
 * @author Arya Shetty, John Greaney-Cheng
 */
public class Date implements Comparable<Date>{
    private int year;
    private int month;
    private int day;

    /**
     * Creates a date object where the date is today
     */
    public Date(){
        Calendar calendar = Calendar.getInstance();
        this.month = calendar.get(Calendar.MONTH) + 1;
        this.day = calendar.get(Calendar.DATE);
        this.year = calendar.get(Calendar.YEAR);
    }

    /**
     * Creates a date object
     * Input Format: 1/02/2003
     *  - Month is 1 (January)
     *  - Day is 2
     *  - Year is 2003
     * @param date the date being stored as a String
     */
    public Date(String date){
        StringTokenizer splitDate = new StringTokenizer(date, "/");
        this.month = Integer.parseInt(splitDate.nextToken());
        this.day = Integer.parseInt(splitDate.nextToken());
        this.year = Integer.parseInt(splitDate.nextToken());
    }

    /**
     * Creates a date object
     * @param month the month of the date
     * @param day the day of the date
     * @param year the year of the date
     */
    public Date(int month, int day, int year){
        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * Compare this date to another
     * Format: d1.compareTo(d2)
     * @param date the date that this date is being compared to (d2)
     * @return 1 if d2's date is before this date,
     *         -1 if d2's date is after this date,
     *         0 if dates are the same
     */
    @Override
    public int compareTo(Date date){
        if (this.year < date.getYear()){
            return -1;
        }
        if (this.year > date.getYear()){
            return 1;
        }
        if (this.month < date.getMonth()){
            return -1;
        }
        if (this.month > date.getMonth()){
            return 1;
        }
        if (this.day < date.getDay()){
            return -1;
        }
        if (this.day > date.getDay()){
            return 1;
        }
        return 0;
    }

    /**
     * Returns String representation of this date
     * @return String representation of this date
     */
    @Override
    public String toString(){
        return this.month + "/" + this.day + "/" + this.year;
    }

    /**
     * Checks if date exists in the calendar
     * Based on the month, if the day is between the first and the last
     * day of the month, the date exists in the calendar.
     * This method assumes February has 28 days then checks
     * if the date is leap day on a leap year
     * @return true if date exists in the calendar, false otherwise
     */
    public boolean isValid(){
        Month month = Month.intToMonth(this.month);
        if (month == null){
            return false;
        }
        if (this.day >= Constant.FIRST_DAY_OF_MONTH.getValue()
                && this.day <= month.getLastDayInMonth()){
            return true;
        }
        if (month == Month.FEBRUARY
                && this.day == Constant.LEAP_DAY.getValue()
                && isLeapYear()){
            return true;
        }
        return false;
    }

    /**
     * Checks if this year is a leap year
     * If the year is not evenly divisible by 4, it's not a leap year
     * If the year is evenly divisible by 4, but not by 100, it's a leap year
     * If the year is evenly divisible by 100, but not by 400, it's not a leap year
     * If the year is evenly divisible by 400, it's a leap year
     * @return true if this year is a leap year, false otherwise
     */
    private boolean isLeapYear(){
        if (this.year % Constant.QUADRENNIAL.getValue() == 0){
            if (this.year % Constant.CENTENNIAL.getValue() == 0){
                if (this.year % Constant.QUARTERCENTENNIAL.getValue() == 0){
                    return true;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Getter method for year of date
     * @return date's year
     */
    public int getYear(){
        return this.year;
    }

    /**
     * Getter method for month of date
     * @return date's month
     */
    public int getMonth(){
        return this.month;
    }

    /**
     * Getter method for day of date
     * @return date's day
     */
    public int getDay(){
        return this.day;
    }
}
