package kalah;

public interface GameConstants {

    //DEFAULT VALUES
    int HOUSES_PER_PLAYER = 6;
    int INTIAL_BEANS_PER_PLAYER = 4;

    //ENUMS
    enum PlayerID {
        P1, P2
    }

    enum MoveResponse {
        PICKED_EMPTY, PICK_AGAIN, TURN_OVER
    }

    //FILE PATHS
}
