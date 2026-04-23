package clueless.commands;

import clueless.Player;
import clueless.board.Space;

public class GuessCommand extends Command {

    public GuessCommand(Player myself, Space space){ super(CommandType.GUESS, myself, space); }

    @Override
    public boolean execute() {
        return false;
    }
}
