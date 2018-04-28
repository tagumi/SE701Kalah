package kalah;

import java.util.ArrayList;

public class Board implements GameConstants {

    private ArrayList<Pit> pits =  new ArrayList<Pit>();

    public  Board(){
        initialiseBoard();
    }

    protected void initialiseBoard(){
        createStores();
        createPlayerHouses(PlayerID.P1, HOUSES_PER_PLAYER);
        createPlayerHouses(PlayerID.P2, HOUSES_PER_PLAYER);
        connectPits();
    }

    private void createStores(){
        pits.add(new Store(PlayerID.P1));
        pits.add(new Store(PlayerID.P2));
    }

    private void createPlayerHouses(PlayerID id, int numHouses){
        for (int i = 0; i < numHouses; i++){
            pits.add(new House(id, 4, i + 1));
        }
    }

    private void connectPits() {
        getPit(PlayerID.P2, true, 0).setNext(getPit(PlayerID.P1, false, 1));

        for (int i = 1; i < 6; i++){
            getPit(PlayerID.P1, false, i).setNext(getPit(PlayerID.P1, false, i + 1));
        }

        getPit(PlayerID.P1, false, 6).setNext(getPit(PlayerID.P1, true, 0));
        getPit(PlayerID.P1, true, 0).setNext(getPit(PlayerID.P2, false, 1));

        for (int i = 1; i < 6; i++){
            getPit(PlayerID.P2, false, i).setNext(getPit(PlayerID.P2, false, i + 1));
        }

        getPit(PlayerID.P2, false, 6).setNext(getPit(PlayerID.P2, true, 0));
    }

    private Pit dropBeans(PlayerID id, int index){
        Pit next = getPit(id, false, index);
        int beans = next.grabBeans();

        while (beans > 0){
           next = next.getNext(id);
           next.dropABean();
           beans--;
        }

        if (next instanceof House){
            if ((next.getOwner() == id) && (next.getBeanCount() == 1) && (getOppositePit(next).getBeanCount() != 0)){
                getPit(id, true, 0).dropBeans(getOppositePit(next).grabBeans());
                getPit(id, true, 0).dropBeans(next.grabBeans());
            }
        }

        return next;
    }

    private Pit getOppositePit(Pit pit){
        int index = (6 - ((House)pit).getIndex()) + 1;

        return getPit(otherPlayer(pit.getOwner()), false, index);
    }

    private boolean isEmpty(PlayerID id, int index){
        return (getPit(id, false, index).getBeanCount() == 0);
    }

    protected Pit getPit(PlayerID id, boolean isStore, int index){
        int pitIndex = 0;
        for (int i = 0; i < pits.size(); i++){
            if (isStore) {
                if (pits.get(i) instanceof Store){
                    if (pits.get(i).getOwner() == id){
                        pitIndex = i;
                    }
                }
            } else {
                if (pits.get(i) instanceof House){
                    if ((pits.get(i).getOwner() == id) && (((House)pits.get(i)).getIndex() == index)){
                        pitIndex = i;
                    }
                }
            }
        }
        return pits.get(pitIndex);
    }

    protected MoveResponse makeMove(String receivedIndex, PlayerID currentPlayer) {

        MoveResponse moveResponse;
        int index = Integer.parseInt(receivedIndex);

        if (checkPitEmpty(currentPlayer, index)){
            moveResponse = MoveResponse.PICKED_EMPTY;
        } else {
            Pit lastDrop = dropBeans(currentPlayer, index);
            if (lastDrop instanceof Store){
                moveResponse = MoveResponse.PICK_AGAIN;
            } else {
                moveResponse = MoveResponse.TURN_OVER;
            }
        }

        return moveResponse;
    }

    private boolean checkPitEmpty (PlayerID currentPlayer, int index){
        return getPit(currentPlayer, false, index).getBeanCount() == 0;
    }

    protected boolean checkAllEmpty (PlayerID currentPlayer){
        boolean allEmpty = true;
        for (int i = 1; i < HOUSES_PER_PLAYER + 1; i++){
            if (getPit(currentPlayer, false, i).getBeanCount() != 0){
                allEmpty = false;
            }
        }
        return allEmpty;
    }

    private PlayerID otherPlayer(PlayerID currentPlayer){
        if (currentPlayer == PlayerID.P1){
            return PlayerID.P2;
        } else {
            return PlayerID.P1;
        }

    }

}
