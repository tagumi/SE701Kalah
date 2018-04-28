package kalah;

public class Store extends Pit {

    /***
     * Constructor for Store subclass
     * @param owner the owner of this Store
     */
    public Store(PlayerID owner) {
        super(owner);
        this.beanCount = 0;
    }
}
