package clueless.commands;

import clueless.Player;
import clueless.board.Space;

public class LookCommand extends Command {

    public LookCommand(Player myself, Space space){ super(CommandType.LOOK, myself, space); }

    @Override
    public boolean execute() {
        return false;
    }
}
