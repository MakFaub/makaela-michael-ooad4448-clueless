package clueless.commands;

import clueless.Player;
import clueless.board.Space;

public class TransportCommand extends Command {
    // private final Space newSpace;

    public TransportCommand(Player myself, Space space){
        super(CommandType.TRANSPORT, myself, space);
    }

    @Override
    public boolean execute() {
        return false;
    }
}
