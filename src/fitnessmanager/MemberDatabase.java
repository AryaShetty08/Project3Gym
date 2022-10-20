package fitnessmanager;

/**
 * MemberDatabase is a class to store and sort gym members
 * Stores the following data:
 *  - Member list
 *  - Size of list
 * @author Arya Shetty, John Greaney-Cheng
 */
public class MemberDatabase {
    private Member[] mlist;
    private int size;               //Number of members in database


    /**
     * Creates a member database object
     * Array starting capacity is 4
     */
    public MemberDatabase() {
        this.size = 0;
        this.mlist = new Member[Constant.STARTING_CAPACITY.getValue()];
    }

    /**
     * Checks if member is stored in database
     * Goes through database list to see if any stored member is
     * equal to given member
     * @param member the member to find in the database
     * @return the index of the member in the list,
     *         -1 if the member is not in the list
     */
    private int find(Member member) {
        for (int i = 0; i < size; i++) {
            if (member.equals(this.mlist[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Increases the size of the member list by four to store new members
     * Creates a new Member array with the increased capacity
     * then stores the old members in the list, and replaces the
     * old database list with the new one
     */
    private void grow() {
        int newLength = Constant.INCREASE_CAPACITY.getValue() + this.size;
        Member[] newList = new Member[newLength];
        for (int i = 0; i < this.mlist.length; i++) {         //Enters old member info into new larger list
            newList[i] = this.mlist[i];
        }
        this.mlist = newList;
    }

    /**
     * Tries to find member in database equal to input parameter
     * Goes through database list to see if any stored member is
     * equal to given member
     * @param member the member to find in the database
     * @return the member stored in the database that's equal to
     *              the input parameter
     *         null if there is no such member in the database
     */
    public Member findMember(Member member){
        if(find(member) != -1){
            return mlist[find(member)];
        }
        return null;
    }

    /**
     * Adds a member into the database
     * First check that the member is not already in the list,
     * then add the member to the end of the list.
     * If the list is at full capacity, grow list by 4
     * @param member the member to add in the list
     * @return true if member was added, false if not added
     */
    public boolean add(Member member) {
        if (find(member) == -1) {
            this.mlist[this.size] = member;
            this.size = this.size + 1;
            if (this.size == this.mlist.length) {             //Checks if mlist is full, if so will automatically use grow()
                grow();
            }
            return true;
        }
        return false;
    }

    /**
     * Removes a specific member from the list
     * First checks to see if member is in the list,
     * if it exists removes the member and moves all members back,
     * and reduces size by 1
     * @param member the member to remove from the list
     * @return true if member was removed, false if not removed
     */
    public boolean remove(Member member) {
        int index = find(member);
        if (index != -1) {
            for (int i = index; i < this.size; i++) {
                this.mlist[i] = this.mlist[i + 1];
            }
            this.size = this.size - 1;
            return true;
        }
        return false;
    }

    /**
     * Checks if this database is empty
     * @return true if this database is empty, false otherwise
     */
    public boolean isEmpty(){
        return this.size == 0;
    }

    /**
     * Prints out unsorted list of members
     */
    public void print() {
        for (int i = 0; i < this.size; i++) {
            System.out.println(this.mlist[i].toString());
        }
    }

    /**
     * Prints out unsorted list of members with their fees and guest passes
     */
    public void printWithFees(){
        for (int i = 0; i < this.size; i++) {
            System.out.println(this.mlist[i].toString() + ", Membership Fee: $"
                    + this.mlist[i].membershipFee());
        }
    }

    /**
     * Prints out sorted list of members
     * Could be ordered by
     *  - county their gym is in (alphabetically),
     *      if it's the same then it compares the zipcode of the gym location.
     *      Ascending order for both county and zipcode.
     *  - expiration date of their membership
     *  - member last name then first name (alphabetically)
     * Sorts the list using a quicksort method,
     * which compares the location stored in the member class,
     * Then prints out list
     * @param category the category to sort members by
     */
    public void sortedPrint(SortCategory category) {
        quickSort(0, this.size - 1, category);
        print();
    }

    /**
     * In-place sorting algorithm: QuickSort
     * Helps sort based on
     *  - county and zip code
     *  - membership expiration date
     *  - name of members
     * First checks if list can be partitioned further,
     * then runs a helper method partition to help sort,
     * then calls itself recursively two times,
     * for the sublist that is greater than
     * and the sublist that is less than the pivot member
     * @param low, the lower bound of the list being sorted
     * @param high, the upper bound of the list being sorted
     * @param category, the category the list is being sorted by
     */
    private void quickSort(int low, int high, SortCategory category){
        if (low < high) {
            int pivot = partition(low, high, category);
            quickSort(low, pivot - 1, category);
            quickSort(pivot + 1, high, category);
        }
    }

    /**
     * A helper method for the QuickSort method
     * Partitions list around pivot element (first element in list)
     * i and j approach from left and right end of list respectively
     * If i and j are found such that:
     *  - ith element should be after pivot element
     *  - jth element should be before pivot element
     *  - i < j
     * Then swap ith and jth elements
     * When i >= j, swap jth element with pivot element.
     * List is now partitioned around pivot element.
     * @param low, the lower bound of the list being partitioned
     * @param high, the upper bound of the list being partitioned
     * @param category, the category the list is being sorted by
     * @return the index of the pivot member
     */
    private int partition(int low, int high, SortCategory category) {
        int i = low;
        int j = high + 1;
        Member swap;
        while (true){
            while (isBefore(this.mlist[++i], this.mlist[low], category)){
                if (i == high){
                    break;
                }
            }
            while (isBefore(this.mlist[low], this.mlist[--j], category)){
                if (j == low){
                    break;
                }
            }
            if (i >= j){
                break;
            }
            swap = this.mlist[i];
            this.mlist[i] = this.mlist[j];
            this.mlist[j] = swap;
        }
        swap = this.mlist[low];
        this.mlist[low] = this.mlist[j];
        this.mlist[j] = swap;
        return j;
    }

    /**
     * A helper method for the Partition method for QuickSort
     * Checks if Member m1 is before Member m2 depending on
     * category members are being sorted by.
     * Potential categories include:
     *  - county and zip code
     *  - membership expiration date
     *  - name of members
     * All categories are sorted in ascending order
     * If invalid category (not in SortCategory) is given, return true
     * @param m1, the member that should come before
     * @param m2, the member that should come after
     * @param category, the category the members are being compared in
     * @return true if m1 should be before or in the same place as m2,
     *         false otherwise
     */
    private boolean isBefore(Member m1, Member m2, SortCategory category){
        switch (category) {
            case COUNTY:
                if (m1.getLocation().getCounty().compareTo(m2.getLocation().getCounty()) < 0){
                    return true;
                }
                if (m1.getLocation().getCounty().compareTo(m2.getLocation().getCounty()) > 0){
                    return false;
                }
                if (m1.getLocation().getZipCode().compareTo(m2.getLocation().getZipCode()) <= 0){
                    return true;
                }
                return false;

            case EXPIRATION_DATE:
                if (m1.getExpire().compareTo(m2.getExpire()) <= 0){
                    return true;
                }
                return false;

            case NAME:
                if (m1.compareTo(m2) <= 0){
                    return true;
                }
                return false;

            default:
                return true;
        }
    }
}
