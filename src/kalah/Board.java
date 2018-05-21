package kalah;

import java.util.ArrayList;

public class Board implements BoardConstants, PlayerConstants {

    private ArrayList<Pit> pits =  new ArrayList<Pit>();

    /***
     * Constructor for Board class
     */
    public  Board(){
        initialiseBoard();
    }

    /***
     * Initialises Board by creating Pit objects for each player and connecting them
     */
    protected void initialiseBoard(){
        createStores();
        createPlayerHouses(PlayerID.P1);
        createPlayerHouses(PlayerID.P2);
        connectPits();
    }

    /***
     * Creates a store for each player and adds them to the pits array list
     */
    private void createStores(){
        pits.add(new Store(PlayerID.P1));
        pits.add(new Store(PlayerID.P2));
    }

    /***
     * Creates houses for each player
     * @param id the id of which player this house is owned by
     */
    private void createPlayerHouses(PlayerID id){
        for (int i = 0; i < HOUSES_PER_PLAYER; i++){
            pits.add(new House(id, 4, i + 1));
        }
    }

    /***
     * Connects pits in pits array list
     * Uses a anti-clockwise game loop setting each pit's next pit, similar to a linked list
     */
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

    /***
     * Drops beans anti-clockwise from a pit selected by a player. Returns the last pit a bean is dropped into.
     * If the last bean is dropped into a pit of the player's, and the opposite pit is not empty, the pit is captured.
     * If a pit is captured the player claims the last dropped bean and the beans in the pit opposite for their store.
     * @param id the id of the player who is making the move
     * @param index the index number of which pit the player has chosen to drop beans from
     * @return the last pit a bean was dropped into
     */
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

    /***
     * Returns the pit on the board opposite a given pit.
     * @param pit the pit that you want to return the opposite of
     * @return the pit opposite the given pit
     */
    private Pit getOppositePit(Pit pit){
        int index = (6 - ((House)pit).getIndex()) + 1;

        return getPit(otherPlayer(pit.getOwner()), false, index);
    }

    /***
     * Returns a boolean reflecting if a given pit is empty.
     * @param id the id of the player who owns the pit
     * @param index the index of the pit to check
     * @return if the pit is empty
     */
    private boolean isEmpty(PlayerID id, int index){
        return (getPit(id, false, index).getBeanCount() == 0);
    }

    /***
     * Returns a pit from the pits array list from given parameters.
     * @param id the id of the player that owns the pit
     * @param isStore a boolean which reflects if the pit is a store
     * @param index the index of the pit to return
     * @return the pit that matches the above parameters
     */
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

    /***
     * Takes a player and their move selection and returns with the response.
     * @param receivedIndex the move index given by the player
     * @param currentPlayer the player making the move
     * @return an enum representing the response of the board to the selected move.
     */
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

    /***
     * Checks if a pit is empty.
     * @param currentPlayer the id of the player who's pit is being checked
     * @param index the index of the pit being checked
     * @return if the pit has no beans in it
     */
    private boolean checkPitEmpty (PlayerID currentPlayer, int index){
        return getPit(currentPlayer, false, index).getBeanCount() == 0;
    }

    /***
     * Checks if all houses owned by a player are empty
     * @param currentPlayer the player who's houses we are checking
     * @return if all houses have a bean count of 0
     */
    protected boolean checkAllEmpty (PlayerID currentPlayer){
        boolean allEmpty = true;
        for (int i = 1; i < HOUSES_PER_PLAYER + 1; i++){
            if (getPit(currentPlayer, false, i).getBeanCount() != 0){
                allEmpty = false;
            }
        }
        return allEmpty;
    }

    /***
     * Returns the other player for a given player.
     * @param currentPlayer enum for the current player
     * @return the enum for the other player.
     */
    private PlayerID otherPlayer(PlayerID currentPlayer){
        if (currentPlayer == PlayerID.P1){
            return PlayerID.P2;
        } else {
            return PlayerID.P1;
        }

    }

    public int housesPerPlayer(){
        return HOUSES_PER_PLAYER;
    }

}
