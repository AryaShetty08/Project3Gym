package fitnessmanager;

/**
 * Family Class is a blueprint for members with family membership
 * Stores the following member data:
 *  - First and Last name
 *  - Date of Birth
 *  - Date of Membership Expiration
 *  - Location of Gym Membership
 *  - Number of Guest Passes
 *  Has different properties than standard member
 *  for membership fee and guest passes
 * @author Arya Shetty, John Greaney-Cheng
 */
public class Family extends Member{
    protected int guestPasses;

    /**
     * Creates a family member object
     * Starts with 1 guest pass (max per family membership)
     * @param fname first name of the member
     * @param lname last name of the member
     * @param dob birthday of the member
     * @param expire expiration date of the member's gym membership
     * @param location the gym the member belongs to
     */
    public Family(String fname, String lname, String dob, Date expire, String location){
        super(fname, lname, dob, expire, location);
        this.guestPasses = Constant.FAMILY_MAX_PASS_NUM.getValue();
    }

    /**
     * Creates a family member object
     * Starts with 1 guest pass (max per family membership)
     * @param fname first name of the member
     * @param lname last name of the member
     */
    public Family(String fname, String lname){
        super(fname, lname);
        this.guestPasses = Constant.FAMILY_MAX_PASS_NUM.getValue();
    }

    /**
     * Creates a family member object
     * Starts with 1 guest pass (max per family membership)
     * @param fname first name of the member
     * @param lname last name of the member
     * @param dob birthday of the member
     */
    public Family(String fname, String lname, String dob){
        super(fname, lname, dob);
        this.guestPasses = Constant.FAMILY_MAX_PASS_NUM.getValue();
    }

    /**
     * Uses guest pass
     * If number of guest passes is zero,
     * guest pass can't be used
     * Otherwise, number of guest passes decreases by one
     * @return true if guest pass is used,
     *         false otherwise
     */
    public boolean useGuestPass() {
        if(guestPasses > 0) {
            this.guestPasses--;
            return true;
        }
        return false;
    }

    /**
     * Returns guest pass for when guest drops out of class
     * If number of guest passes is less than max value,
     * add one to guest passes
     * @return true if guest pass is returned,
     *         false otherwise
     */
    public boolean returnGuestPass() {
        if(guestPasses < Constant.FAMILY_MAX_PASS_NUM.getValue()) {
            this.guestPasses++;
            return true;
        }
        return false;
    }

    /**
     * Returns String representation of this member
     * Format ex: April March, DOB: 3/31/1990, Membership expires 6/30/2023,
     *            Location: PISCATAWAY, 08854, MIDDLESEX, (Family) Guest-pass
     *            remaining: 1
     * @return String representation of this member
     */
    @Override
    public String toString(){
        if (super.expire.compareTo(new Date()) < 0){                  //Check if membership expiration date has passed
            return super.fname + " " + super.lname + ", DOB: " + super.dob.toString()
                    +  ", Membership expired " + super.expire.toString()
                    +  ", Location: " + super.location.toString() + ", (Family) Guest-pass remaining: "
                    +  this.guestPasses;
        }
        return super.fname + " " + super.lname + ", DOB: " + super.dob.toString()
                +  ", Membership expires " + super.expire.toString()
                +  ", Location: " + super.location.toString() + ", (Family) Guest-pass remaining: "
                +  this.guestPasses;
    }

    /**
     * Returns membership fee
     * Per Month fee is paid quarterly with a one-time fee
     * @return membership fee
     */
    @Override
    public double membershipFee(){
        return ((Fee.FAMILY_PER_MONTH.getPrice() * Constant.QUARTERLY_NUM_MONTHS.getValue())
                    + Fee.ONE_TIME_FEE.getPrice());
    }
}
