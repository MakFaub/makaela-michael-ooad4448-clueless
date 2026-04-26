package clueless.commands;

import clueless.Player;
import clueless.board.Space;

public abstract class Command implements ICommand {
    protected CommandType type;
    protected final Player player;
    protected final Space space;

    public Command(CommandType type, Player player, Space space) {
        this.type = type;
        this.player = player;
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