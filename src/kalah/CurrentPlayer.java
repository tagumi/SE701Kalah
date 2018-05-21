package kalah;

public class CurrentPlayer implements PlayerConstants{

    private PlayerID currentPlayer;

    /***
     * Constructor for the CurrentPlayer class.
     * @param firstPlayer the PlayerID of the first player
     */
    protected CurrentPlayer(PlayerID firstPlayer){
        this.currentPlayer = firstPlayer;
    }

    /***
     * Advances the current player to the next player.
     */
    protected void advanceTurn(){
        if (this.currentPlayer == PlayerID.P1){
            this.currentPlayer = PlayerID.P2;
        } else {
            this.currentPlayer = PlayerID.P1;
        }
    }

    /***
     * Gets the current player.
     * @return the id of the player who's turn it currently is
     */
    protected PlayerID getCurrentPlayer(){
        return this.currentPlayer;
    }

    /***
     * Returns the current player's number as a string.
     * @return a string of the current player's number
     */
    protected String getNumber(){
        if (this.currentPlayer == PlayerID.P1){
            return "1";
        } else {
            return "2";
        }
    }

}
