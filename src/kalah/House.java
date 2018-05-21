package kalah;

public class House extends Pit {

    private int index;

    /***
     * Constructor for the Pit subclass.
     * @param owner the PlayerID enum of the house's owner
     * @param beanCount the number of beans to initialise the house with
     * @param index the index number of the house
     */
    public House(PlayerID owner, int beanCount, int index) {
        super(owner);
        this.index = index;
    }

    /***
     * Getter for house index
     * @return the house index
     */
    public int getIndex() {
        return index;
    }

}
