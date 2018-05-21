package kalah;

public abstract class Pit implements BoardConstants, PlayerConstants{

    int beanCount;

    private PlayerID owner;
    private Pit next;

    /***
     * Constructor for Pit class.
     * @param owner the enum of the player who owns the pit
     */
    protected Pit(PlayerID owner){
        this.owner = owner;
        this.beanCount = INITIAL_BEANS_PER_PLAYER;
    }

    /***
     * Getter for owner enum.
     * @return the enum corresponding to the player that owns this pit
     */
    protected PlayerID getOwner() {
        return owner;
    }

    /***
     * Setter for the next pit.
     * @param p pit which is the next in our game board when dropping beans
     */
    public void setNext(Pit p){
        this.next = p;
    }

    /***
     * Getter for the next pit. Will return the next next pit if the following pit is the other player's store.
     * @param id the id of the player making the getNext call
     * @return the next pit to drop a bean into
     */
    public Pit getNext(PlayerID id){

        if ((this.next instanceof Store) && (this.next.getOwner() != id))
            return this.next.getNext(id);
        else {
            return this.next;
        }
    }

    /***
     * Getter for the beanCount int.
     * @return the number of beans this pit has
     */
    public int getBeanCount() {
        return beanCount;
    }

    /***
     * Increases bean count by 1
     */
    public void dropABean() {
        this.beanCount++;
    }

    /***
     * Grabs all beans in pit
     * @return sets number of beans in pit to 0 and returns how many beans were in the pit
     */
    public int grabBeans() {
        int grabbedBeans = this.beanCount;
        this.beanCount = 0;
        return grabbedBeans;
    }

    /***
     * Increases beanCount by a given int.
     * @param beans how many beans we are adding
     */
    public void dropBeans(int beans) {
        this.beanCount += beans;

    }

}
