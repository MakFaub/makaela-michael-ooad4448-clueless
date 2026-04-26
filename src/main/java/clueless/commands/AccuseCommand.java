package clueless.commands;

import clueless.Player;
import clueless.board.Space;
import clueless.cards.Envelope;

public class AccuseCommand extends Command {
    private final Envelope envelope;

    public AccuseCommand(Player myself, Space space, Envelope envelope){
        super(CommandType.ACCUSE, myself, space);
        this.envelope = envelope;
    }

    @Override
    public boolean execute() {
        return false;
    }
}