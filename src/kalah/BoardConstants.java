package kalah;

public interface BoardConstants {

    //DEFAULT VALUES
    int HOUSES_PER_PLAYER = 6;
    int INITIAL_BEANS_PER_PLAYER = 4;

    //ENUMS
    enum MoveResponse {
        PICKED_EMPTY, PICK_AGAIN, TURN_OVER
    }

}
