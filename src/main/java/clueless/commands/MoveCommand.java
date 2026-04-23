package clueless.commands;

import clueless.Player;
import clueless.board.Space;

public class MoveCommand extends Command {
    private final Space newSpace;

    public MoveCommand(Player myself, Space space, Space newSpace) {
        super(CommandType.MOVE, myself, space);
        this.newSpace = newSpace;
    }

    @Override
    public boolean execute(){ return false; }

}
