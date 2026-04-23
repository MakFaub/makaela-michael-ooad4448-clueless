package clueless.commands;

import clueless.Player;
import clueless.board.Space;
import clueless.pieces.IPiece;
import clueless.pieces.WeaponPiece;

public class TakeCommand extends Command {
    private IPiece piece;

    public TakeCommand(Player myself, Space space, IPiece piece){
        super(CommandType.TAKE, myself, space);
        this.piece = piece;
    }

    @Override
    public boolean execute() {
        return false;
    }
}
