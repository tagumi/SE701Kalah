package kalah;

public class House extends Pit {

    private int index;

    public House(PlayerID owner, int beanCount, int index) {
        super(owner);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
