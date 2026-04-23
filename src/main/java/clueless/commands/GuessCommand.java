package clueless.commands;

import clueless.Player;
import clueless.board.Space;

public class GuessCommand extends Command {

    public GuessCommand(Player player, Space space){ super(CommandType.GUESS, player, space); }

    @Override
    public boolean execute() {
        return false;
    }
}
