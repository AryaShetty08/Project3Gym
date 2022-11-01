package fitnessmanager;

/**
 * Location Enum Class holds constant Locations, expressed by zipcode and county of gym
 * Stores the following data:
 *  - zipCode
 *  - county
 * @author Arya Shetty, John Greaney-Cheng
 */
public enum Location {

    BRIDGEWATER("08807", "SOMERSET"),
    EDISON("08837", "MIDDLESEX"),
    FRANKLIN("08873", "SOMERSET"),
    PISCATAWAY("08854", "MIDDLESEX"),
    SOMERVILLE("08876", "SOMERSET");

    private final String zipCode;
    private final String county;

    /**
     * Creates a Location constant object
     * @param zipCode, a String of digits, representing gym location
     * @param county, a String representing county of gym
     */
    Location(String zipCode, String county){
        this.zipCode = zipCode;
        this.county = county;
    }

    /**
     * Getter method for Location of gym zipCode
     * @return gym location's zipCode
     */
    public String getZipCode(){
        return this.zipCode;
    }

    /**
     * Getter method for Location of gym county
     * @return gym location's county
     */
    public String getCounty(){
        return this.county;
    }

    /**
     * Method takes in location name and returns a location enum
     * @param str, string that represents location name
     * @return location string was referring to if it exists,
     *         null otherwise
     */
    public static Location stringToLocation(String str){
        for (Location location: Location.values()){
            if (str.equalsIgnoreCase(location.name())){
                return location;
            }
        }
        return null;
    }


    /**
     * Returns String representation of this location
     * Format ex: PISCATAWAY, 08854, MIDDLESEX
     * @return String representation of this location
     */
    @Override
    public String toString(){
        return this.name() + ", " + this.getZipCode() + ", " + this.getCounty();
    }

}
