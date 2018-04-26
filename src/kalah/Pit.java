package kalah;

public abstract class Pit implements GameConstants{

    int beanCount;

    private PlayerID owner;
    private Pit next;


    protected Pit(PlayerID owner){
        this.owner = owner;
        this.beanCount = INTIAL_BEANS_PER_PLAYER;
    }

    protected PlayerID getOwner() {
        return owner;
    }

    public void setNext(Pit p){
        this.next = p;
    }

    public Pit getNext(PlayerID id){

        if ((this.next instanceof Store) && (this.next.getOwner() != id))
            return this.next.getNext(id);
        else {
            return this.next;
        }
    }

    public int getBeanCount() {
        return beanCount;
    }

    public void dropABean() {
        this.beanCount++;
    }

    public int grabBeans() {
        int grabbedBeans = this.beanCount;
        this.beanCount = 0;
        return grabbedBeans;
    }

    public void dropBeans(int beans) {
        this.beanCount += beans;

    }

}
