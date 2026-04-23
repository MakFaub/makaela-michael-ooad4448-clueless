package clueless.commands;

import clueless.Player;
import clueless.board.Space;

public class SuggestCommand extends Command {

    public SuggestCommand(Player myself, Space space){ super(CommandType.SUGGEST, myself, space); }

    @Override
    public boolean execute() {
        return false;
    }
}
