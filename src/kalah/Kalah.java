package kalah;

import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;

/**
 * This class is the starting point for a Kalah implementation using
 * the test infrastructure.
 */
public class Kalah implements BoardConstants, PlayerConstants{

	private Board gameBoard = new Board();
	private CurrentPlayer currentPlayer = new CurrentPlayer(PlayerID.P1);

	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}

    /***
     * Plays a game of kalah
     */
	public void play(IO io) {
		//Setup
        IOHandler boardPrinter = new IOHandler(io);
        gameBoard.initialiseBoard();

        boardPrinter.printState(gameBoard);
        boolean gameFinished = false;
        String response = boardPrinter.inputResponse("Player P1's turn - Specify house number or 'q' to quit: ");

		//Game loop
		while(true){
			if (response.equals("q")) break;

			if (validInput(response)) {
                MoveResponse moveResponse = gameBoard.makeMove(response, currentPlayer.getCurrentPlayer());
                if (moveResponse == MoveResponse.TURN_OVER) {
                    currentPlayer.advanceTurn();
                    boardPrinter.printState(gameBoard);
                    if (gameBoard.checkAllEmpty(currentPlayer.getCurrentPlayer())) {
                        gameFinished = true;
                        break;
                    } else {
                        response = boardPrinter.inputResponse("Player P" + currentPlayer.getNumber() + "'s turn - Specify house number or 'q' to quit: ");
                    }
                } else if (moveResponse == MoveResponse.PICKED_EMPTY) {
                    boardPrinter.printString("House is empty. Move again.");
                    boardPrinter.printState(gameBoard);
                    response = boardPrinter.inputResponse("Player P" + currentPlayer.getNumber() + "'s turn - Specify house number or 'q' to quit: ");
                } else {
                    boardPrinter.printState(gameBoard);
                    if (gameBoard.checkAllEmpty(currentPlayer.getCurrentPlayer())){
                        gameFinished = true;
                        break;
                    }
                    response = boardPrinter.inputResponse("Player P" + currentPlayer.getNumber() + "'s turn - Specify house number or 'q' to quit: ");
                }
            }
		}

        boardPrinter.printString("Game over");
        boardPrinter.printState(gameBoard);
		if (gameFinished){
            boardPrinter.printResults(gameBoard);
        }
	}

    /***
     * Checks if an input string is valid as a move.
     * @param response the input string
     * @return if the input string is valid as a move
     */
    private boolean validInput (String response){
        return (response.length() == 1);
    }

}
