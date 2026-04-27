package clueless.commands;

import clueless.Player;
import clueless.board.Board;
import clueless.board.BoardInfoDisplay;
import clueless.board.Space;

import java.util.List;

public class BoardInfoCommand extends Command{
    private final Board board;
    private final List<Player> players;

    public BoardInfoCommand(Player player, Space space, Board board, List<Player> players) {
        super(CommandType.SHOW_BOARD_INFO, player, space);
        this.board = board;
        this.players = players;
    }

    @Override
    public boolean execute() {
        System.out.println(BoardInfoDisplay.render(board, players));
        return true;
    }
}
