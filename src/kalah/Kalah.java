package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import org.omg.CORBA.Current;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah implements GameConstants{

	private Board gameBoard = new Board();
	private CurrentPlayer currentPlayer = new CurrentPlayer(PlayerID.P1);

	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
	public void play(IO io) {
		// Replace what's below with your implementation

		//setup
        gameBoard.initialiseBoard();

        printState(io);

        boolean gameFinished = false;
        String response = io.readFromKeyboard("Player P1's turn - Specify house number or 'q' to quit: ");

		//gameloop
		while(true){
			if (response.equals("q")) break;

			if (validInput(response)) {
                MoveResponse moveResponse = gameBoard.makeMove(response, currentPlayer.getCurrentPlayer());
                if (moveResponse == MoveResponse.TURN_OVER) {
                    currentPlayer.advanceTurn();
                    printState(io);
                    if (gameBoard.checkAllEmpty(currentPlayer.getCurrentPlayer())) {
                        gameFinished = true;
                        break;
                    } else {
                        response = io.readFromKeyboard("Player P" + currentPlayer.getNumber() + "'s turn - Specify house number or 'q' to quit: ");
                    }
                } else if (moveResponse == MoveResponse.PICKED_EMPTY) {
                    io.println("House is empty. Move again.");
                    printState(io);
                    response = io.readFromKeyboard("Player P" + currentPlayer.getNumber() + "'s turn - Specify house number or 'q' to quit: ");
                } else {
                    printState(io);
                    if (gameBoard.checkAllEmpty(currentPlayer.getCurrentPlayer())){
                        gameFinished = true;
                        break;
                    }
                    response = io.readFromKeyboard("Player P" + currentPlayer.getNumber() + "'s turn - Specify house number or 'q' to quit: ");
                }
            }
		}

        io.println("Game over");
        printState(io);
		if (gameFinished){
            printResults(io);
        }
	}

	//TODO check input is VALID
	private boolean validInput (String response){
	    return true;
    }

    private int getPlayerScore(PlayerID playerID){
        int score = gameBoard.getPit(playerID, true, 0).getBeanCount();

        for (int i = 1; i < HOUSES_PER_PLAYER + 1; i++) {
            score += gameBoard.getPit(playerID, false, i).getBeanCount();
        }

        return score;
    }

    private void printResults (IO io){
	    int scoreP1 = getPlayerScore(PlayerID.P1);
        int scoreP2 = getPlayerScore(PlayerID.P2);
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

	private void printState(IO io){
	    //in
	    int p1_beans[] = new int [7];
	    int p2_beans[] = new int [7];

	    p1_beans[0] = gameBoard.getPit(GameConstants.PlayerID.P1, true, 0).getBeanCount();
        p2_beans[0] = gameBoard.getPit(GameConstants.PlayerID.P2, true, 0).getBeanCount();

        for (int i = 0; i < 6; i++){
            p1_beans[i + 1] = gameBoard.getPit(GameConstants.PlayerID.P1, false, i + 1).getBeanCount();
            p2_beans[i + 1] = gameBoard.getPit(GameConstants.PlayerID.P2, false, i + 1).getBeanCount();
        }

        //print scores from gameBoard, formatted for two character spaces
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6[" + formatBeans(p2_beans[6]) + "] | 5[" + formatBeans(p2_beans[5]) + "] | 4[" + formatBeans(p2_beans[4]) + "] | 3[" + formatBeans(p2_beans[3]) + "] | 2[" + formatBeans(p2_beans[2]) + "] | 1[" + formatBeans(p2_beans[1]) + "] | " + formatBeans(p1_beans[0]) + " |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("| " + formatBeans(p2_beans[0]) + " | 1[" + formatBeans(p1_beans[1]) + "] | 2[" + formatBeans(p1_beans[2]) + "] | 3[" + formatBeans(p1_beans[3]) + "] | 4[" + formatBeans(p1_beans[4]) + "] | 5[" + formatBeans(p1_beans[5]) + "] | 6[" + formatBeans(p1_beans[6]) + "] | P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }

    private String formatBeans(int beanCount){
	    String beanString = Integer.toString(beanCount);
	    if (beanCount < 10){
            beanString = " " + beanString;
        }

	    return beanString;
    }

}
