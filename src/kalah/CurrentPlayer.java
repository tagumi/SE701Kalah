package kalah;

public class CurrentPlayer implements GameConstants{

    private PlayerID currentPlayer;


    protected CurrentPlayer(PlayerID firstPlayer){
        this.currentPlayer = firstPlayer;
    }

    protected void advanceTurn(){
        if (this.currentPlayer == PlayerID.P1){
            this.currentPlayer = PlayerID.P2;
        } else {
            this.currentPlayer = PlayerID.P1;
        }
    }

    protected PlayerID getCurrentPlayer(){
        return this.currentPlayer;
    }

    protected String getNumber(){
        if (this.currentPlayer == PlayerID.P1){
            return "1";
        } else {
            return "2";
        }
    }

}
