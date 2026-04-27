package clueless.board;

import clueless.Player;

import java.util.List;

public class TestBoardDisplay extends BoardDisplay {

    private final Board board;

    public TestBoardDisplay(Board board, List<Player> players) {
        super(players);
        this.board = board;
    }

    @Override
    protected Space[][] buildGrid() {
        return buildGridFromConnections(board.getSpace("Room A"), board);
    }
}
