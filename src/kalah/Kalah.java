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
	private void play(IO io) {
		// Replace what's below with your implementation

		//setup
        gameBoard.initialiseBoard();

        printState(io);

        boolean gameFinished = false;
        String response = io.readFromKeyboard("Player 1's turn - Specify house number or 'q' to quit: ");

		//gameloop
		while(true){
			if (response.equals("q")) break;

			if (validInput(response)) {
                MoveResponse moveResponse = gameBoard.makeMove(response, currentPlayer.getCurrentPlayer());
                if (moveResponse ==  MoveResponse.ALL_EMPTY){
                    gameFinished = true;
                    break;
                } else if (moveResponse == MoveResponse.CAPTURE) {
                    currentPlayer.advanceTurn();
                    printState(io);
                    io.println("Player " + currentPlayer.getNumber() + "'s turn - Specify house number or 'q' to quit: ");
                } else if (moveResponse == MoveResponse.PICKED_EMPTY) {
                    io.println("House is empty. Move again.");
                    printState(io);
                } else {
                    printState(io);
                    io.println("Player " + currentPlayer.getNumber() + "'s turn - Specify house number or 'q' to quit: ");
                }
            }

            response = io.readFromKeyboard("");

		}

		if (gameFinished){
            io.println("Game over");
            printState(io);
            printResults(io);
        }



	}

	//TODO check input is VALID
	private boolean validInput (String response){
	    return true;
    }

    private void printResults (IO io){
	    int scoreP1 = gameBoard.getPit(GameConstants.PlayerID.P1, true, 0).getBeanCount();
        int scoreP2 = gameBoard.getPit(GameConstants.PlayerID.P2, true, 0).getBeanCount();
        io.println("\tplayer 1:" + scoreP1);
        io.println("\tplayer 2:" + scoreP2);

        if (scoreP1 > scoreP2){
            io.println("Player 1 wins!");
        } else {
            io.println("Player 2 wins!");
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


        //printscores from gameBoard, right now just prints default
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6[ " + p2_beans[6] + "] | 5[ " + p2_beans[5] + "] | 4[ " + p2_beans[4] + "] | 3[ " + p2_beans[3] + "] | 2[ " + p2_beans[2] + "] | 1[ " + p2_beans[1] + "] |  " + p1_beans[0] + " |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("|  " + p2_beans[0] + " | 1[ " + p1_beans[1] + "] | 2[ " + p1_beans[2] + "] | 3[ " + p1_beans[3] + "] | 4[ " + p1_beans[4] + "] | 5[ " + p1_beans[5] + "] | 6[ " + p1_beans[6] + "] | P1 |");
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
    }

}
