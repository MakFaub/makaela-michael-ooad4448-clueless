package clueless.commands;

import clueless.Player;
import clueless.board.Space;
import clueless.pieces.IPiece;
import clueless.pieces.PieceType;
import clueless.pieces.WeaponPiece;

public class TakeCommand extends Command {
    private IPiece piece;

    public TakeCommand(Player player, Space space, IPiece piece){
        super(CommandType.TAKE, player, space);
        this.piece = piece;
    }

    // TODO: add logger messages and EventBus
    @Override
    public boolean execute() {
        if (!space.contains(player.getPlayerPiece())) {
            return false;
        }
        if (!space.contains(piece)) {
            return false;
        }

        // player can only have 1 weapon and 1 artifact in hand
        PieceType pieceType = piece.getType();
        if (player.getPiecesInHand().contains())

        space.removePiece(piece);

        return true;
    }

    @Override
    public String optionString() {
        return piece.getName();
    }
}
