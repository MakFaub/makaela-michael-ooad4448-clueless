package clueless.commands;

import clueless.Player;
import clueless.board.Space;

public abstract class Command implements ICommand {
    protected CommandType type;
    protected final Player myself;
    protected final Space space;

    public Command(CommandType type, Player myself, Space space) {
        this.type = type;
        this.myself = myself;
        this.space = space;
    }

    @Override
    public CommandType getType() {
        return type;
    }

    @Override
    public String optionString() {
        return "";
    }

}
