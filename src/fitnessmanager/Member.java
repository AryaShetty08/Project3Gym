package fitnessmanager;

/**
 * Member Class is a blueprint for member objects
 * Stores the following member data:
 *  - First and Last name
 *  - Date of Birth
 *  - Date of Membership Expiration
 *  - Location of Gym Membership
 * @author Arya Shetty, John Greaney-Cheng
 */
public class Member implements Comparable<Member>{
    protected String fname;
    protected String lname;
    protected Date dob;
    protected Date expire;
    protected Location location;

    /**
     * Creates a standard member object
     * @param fname first name of the member
     * @param lname last name of the member
     * @param dob birthday of the member
     * @param expire expiration date of the member's gym membership
     * @param location the gym the member belongs to
     */
    public Member(String fname, String lname, String dob, String expire, String location){
        this.fname = fname;
        this.lname = lname;
        this.dob = new Date(dob);
        this.expire = new Date(expire);
        this.location = Location.stringToLocation(location);
    }

    /**
     * Creates a standard member object
     * @param fname first name of the member
     * @param lname last name of the member
     * @param dob birthday of the member
     * @param expire expiration date of the member's gym membership
     * @param location the gym the member belongs to
     */
    public Member(String fname, String lname, String dob, Date expire, String location){
        this.fname = fname;
        this.lname = lname;
        this.dob = new Date(dob);
        this.expire = expire;
        this.location = Location.stringToLocation(location);
    }

    /**
     * Creates a standard member object
     * @param fname first name of the member
     * @param lname last name of the member
     * @param dob birthday of the member
     */
    public Member(String fname, String lname, String dob){
        this.fname = fname;
        this.lname = lname;
        this.dob = new Date(dob);
    }

    /**
     * Creates a standard member object
     * @param fname first name of the member
     * @param lname last name of the member
     */
    public Member(String fname, String lname){
        this.fname = fname;
        this.lname = lname;
    }

    /**
     * Returns membership fee
     * Per Month fee is paid quarterly with a one-time fee
     * @return membership fee
     */
    public double membershipFee(){
        return (Fee.STANDARD_PER_MONTH.getPrice() * Constant.QUARTERLY_NUM_MONTHS.getValue())
                    + Fee.ONE_TIME_FEE.getPrice();
    }

    /**
     * Returns String representation of this member
     * Format ex: April March, DOB: 3/31/1990, Membership expires 6/30/2023,
     *            Location: PISCATAWAY, 08854, MIDDLESEX
     * @return String representation of this member
     */
    @Override
    public String toString(){
        if (this.expire.compareTo(new Date()) < 0){                  //Check if membership expiration date has passed
            return this.fname + " " + this.lname + ", DOB: " + this.dob.toString()
                              +  ", Membership expired " + this.expire.toString()
                              +  ", Location: " + this.location.toString();
        }
        return this.fname + " " + this.lname + ", DOB: " + this.dob.toString()
                          +  ", Membership expires " + this.expire.toString()
                          +  ", Location: " + this.location.toString();
    }

    /**
     * Determine equality between this member and another
     * Two members are equal if
     *  - They have the same first name
     *  - They have the same last name
     *  - They have the same birthday
     * Names are not case-sensitive
     * @param obj the member to check equality with
     * @return true if members are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Member){
            Member member = (Member) obj;
            if (this.fname.equalsIgnoreCase(member.getFname())
                    && this.lname.equalsIgnoreCase(member.getLname())
                    && this.dob.compareTo(member.getDob()) == 0){
                return true;
            }
        }
        return false;
    }

    /**
     * Compares the name of this member to another
     * Format: m1.compareTo(m2)
     * First compare last names and if they're the
     * same, compare first names
     * Note: Names are not case-sensitive when compared
     * to one another, all comparisons are done with
     * names converted to lowercase
     * @param member the member to compare names with (m2)
     * @return >0 if m2's name is greater lexicographically,
     *         <0 if m2's name is lesser lexicographically,
     *         =0 if m2's name is same as this member
     */
    @Override
    public int compareTo(Member member){
        if (this.lname.compareToIgnoreCase(member.getLname()) != 0){
            return this.lname.compareToIgnoreCase(member.getLname());
        }
        return this.fname.compareToIgnoreCase(member.getFname());
    }

    /**
     * Getter method for member's first name
     * @return member's first name
     */
    public String getFname(){
        return this.fname;
    }

    /**
     * Getter method for member's last name
     * @return member's last name
     */
    public String getLname(){
        return this.lname;
    }

    /**
     * Getter method for member's birthday
     * @return member's birthday
     */
    public Date getDob(){
        return this.dob;
    }

    /**
     * Getter method for member's gym membership expiration date
     * @return member's gym membership expiration date
     */
    public Date getExpire(){
        return this.expire;
    }

    /**
     * Getter method for member's gym membership location
     * @return member's gym membership location
     */
    public Location getLocation(){
        return this.location;
    }
}
