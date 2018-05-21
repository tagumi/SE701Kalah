package kalah;
import com.qualitascorpus.testsupport.IO;

public class IOHandler implements PlayerConstants{

    private IO io;

    public IOHandler(IO io){
        this.io = io;
    }

    /***
     * Prints the current state of the board.
     * @param gameBoard the board of which we are printing the state of
     */
    public void printState(Board gameBoard) {
        //in
        int p1_beans[] = new int [7];
        int p2_beans[] = new int [7];

        p1_beans[0] = gameBoard.getPit(PlayerID.P1, true, 0).getBeanCount();
        p2_beans[0] = gameBoard.getPit(PlayerID.P2, true, 0).getBeanCount();

        for (int i = 0; i < 6; i++){
            p1_beans[i + 1] = gameBoard.getPit(PlayerID.P1, false, i + 1).getBeanCount();
            p2_beans[i + 1] = gameBoard.getPit(PlayerID.P2, false, i + 1).getBeanCount();
        }

        //print scores from gameBoard, formatted for two character spaces
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6[" + formatBeans(p2_beans[6]) + "] | 5[" + formatBeans(p2_beans[5]) + "] | 4[" + formatBeans(p2_beans[4]) + "] | 3[" + formatBeans(p2_beans[3]) + "] | 2[" + formatBeans(p2_beans[2]) + "] | 1[" + formatBeans(p2_beans[1]) + "] | " + formatBeans(p1_beans[0]) + " |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("| " + formatBeans(p2_beans[0]) + " | 1[" + formatBeans(p1_beans[1]) + "] | 2[" + formatBeans(p1_beans[2]) + "] | 3[" + formatBeans(p1_beans[3]) + "] | 4[" + formatBeans(p1_beans[4]) + "] | 5[" + formatBeans(p1_beans[5]) + "] | 6[" + formatBeans(p1_beans[6]) + "] | P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }

    /***
     * Formats an integer to a two character string for use in the print state function.
     * @param beanCount the number of beans we are formatting to print
     * @return the string to print
     */
    private String formatBeans(int beanCount){
        String beanString = Integer.toString(beanCount);
        if (beanCount < 10){
            beanString = " " + beanString;
        }

        return beanString;
    }

    /***
     * Gets a player's score and returns it as an int.
     * @param playerID the id of the player who's score we are going to get
     * @return an int which is the given player's score
     */
    private int getPlayerScore(Board gameBoard, PlayerID playerID){
        int score = gameBoard.getPit(playerID, true, 0).getBeanCount();

        for (int i = 1; i < gameBoard.housesPerPlayer() + 1; i++) {
            score += gameBoard.getPit(playerID, false, i).getBeanCount();
        }

        return score;
    }

    /***
     * Prints the results after a completed game.
     * @param gameBoard the board of which we are printing results of
     */
    public void printResults (Board gameBoard){
        int scoreP1 = getPlayerScore(gameBoard, PlayerID.P1);
        int scoreP2 = getPlayerScore(gameBoard, PlayerID.P2);
        io.println("\tplayer 1:" + scoreP1);
        io.println("\tplayer 2:" + scoreP2);

        if (scoreP1 > scoreP2){
            io.println("Player 1 wins!");
        } else if (scoreP1 < scoreP2){
            io.println("Player 2 wins!");
        } else {
            io.println("A tie!");
        }
    }

    public void printString(String s){
        io.println(s);
    }

    public String inputResponse(String s){
        return io.readFromKeyboard(s);
    }

}
