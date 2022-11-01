package fitnessmanager;

/**
 * Fee Enum Class holds all prices for membership fee calculations
 * Stores the price corresponding to each fee
 * @author Arya Shetty, John Greaney-Cheng
 */
public enum Fee {
    ONE_TIME_FEE(29.99),
    STANDARD_PER_MONTH(39.99),
    FAMILY_PER_MONTH(59.99);

    private final double price;

    /**
     * Creates a Fee
     * @param price the corresponding price of each fee
     */
    Fee(double price){
        this.price = price;
    }

    /**
     * Getter method for price of fee
     * @return fee's price
     */
    public double getPrice(){
        return this.price;
    }
}
