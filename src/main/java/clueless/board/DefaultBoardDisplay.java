package clueless.board;

import clueless.Player;

import java.util.List;

public class DefaultBoardDisplay extends BoardDisplay {

    private final Board board;

    public DefaultBoardDisplay(Board board, List<Player> players) {
        super(players);
        this.board = board;
    }

    @Override
    protected Space[][] buildGrid() {
        return buildGridFromConnections(board.getSpace("Study"), board);
    }
}