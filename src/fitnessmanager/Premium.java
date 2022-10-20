package fitnessmanager;

/**
 * Premium Class is a blueprint for members with premium membership
 * Stores the following member data:
 *  - First and Last name
 *  - Date of Birth
 *  - Date of Membership Expiration
 *  - Location of Gym Membership
 *  - Number of Guest Passes
 *  Has different properties than family membership
 *  for membership fee and guest passes
 * @author Arya Shetty, John Greaney-Cheng
 */
public class Premium extends Family{

    /**
     * Creates a premium member object
     * Starts with 3 guest passes (max per premium membership)
     * @param fname first name of the member
     * @param lname last name of the member
     * @param dob birthday of the member
     * @param expire expiration date of the member's gym membership
     * @param location the gym the member belongs to
     */
    public Premium(String fname, String lname, String dob, Date expire, String location){
        super(fname, lname, dob, expire, location);
        this.guestPasses = Constant.PREMIUM_MAX_PASS_NUM.getValue();
    }

    /**
     * Creates a premium member object
     * Starts with 3 guest passes (max per premium membership)
     * @param fname first name of the member
     * @param lname last name of the member
     */
    public Premium(String fname, String lname){
        super(fname, lname);
        this.guestPasses = Constant.PREMIUM_MAX_PASS_NUM.getValue();
    }

    /**
     * Creates a premium member object
     * Starts with 3 guest passes (max per premium membership)
     * @param fname first name of the member
     * @param lname last name of the member
     * @param dob birthday of the member
     */
    public Premium(String fname, String lname, String dob){
        super(fname, lname, dob);
        this.guestPasses = Constant.PREMIUM_MAX_PASS_NUM.getValue();
    }

    /**
     * Returns guest pass for when guest drops out of class
     * If number of guest passes is less than max value,
     * add one to guest passes
     * @return true if guest pass is returned,
     *         false otherwise
     */
    @Override
    public boolean returnGuestPass(){
        if(guestPasses < Constant.PREMIUM_MAX_PASS_NUM.getValue()) {
            this.guestPasses++;
            return true;
        }
        return false;
    }

    /**
     * Returns String representation of this member
     * Format ex: April March, DOB: 3/31/1990, Membership expires 6/30/2023,
     *            Location: PISCATAWAY, 08854, MIDDLESEX, (Premium) Guest-pass
     *            remaining: 3
     * @return String representation of this member
     */
    @Override
    public String toString(){
        if (super.expire.compareTo(new Date()) < 0){                  //Check if membership expiration date has passed
            return super.fname + " " + super.lname + ", DOB: " + super.dob.toString()
                    +  ", Membership expired " + super.expire.toString()
                    +  ", Location: " + super.location.toString() + ", (Premium) Guest-pass remaining: "
                    +  super.guestPasses;
        }
        return super.fname + " " + super.lname + ", DOB: " + super.dob.toString()
                +  ", Membership expires " + super.expire.toString()
                +  ", Location: " + super.location.toString() + ", (Premium) Guest-pass remaining: "
                +  super.guestPasses;
    }

    /**
     * Returns membership fee
     * Per Month fee is paid annually with no one-time fee
     * and one month free
     * @return membership fee
     */
    @Override
    public double membershipFee(){
        return Fee.FAMILY_PER_MONTH.getPrice() * (Constant.ANNUALLY_NUM_MONTHS.getValue() - 1);
    }

}
