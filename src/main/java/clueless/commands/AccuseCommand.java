package clueless.commands;

import clueless.Player;
import clueless.board.Space;

public class AccuseCommand extends Command {

    public AccuseCommand(Player myself, Space space){ super(CommandType.ACCUSE, myself, space); }

    @Override
    public boolean execute() {
        return false;
    }
}
