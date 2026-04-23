package clueless.commands;

import clueless.Player;
import clueless.board.Space;

public class SummonCommand extends Command {

    public SummonCommand(Player myself, Space space){ super(CommandType.SUMMON, myself, space); }

    @Override
    public boolean execute() {
        return false;
    }
}
