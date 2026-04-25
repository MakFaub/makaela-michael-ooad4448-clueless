package clueless.commands;

import clueless.Player;
import clueless.board.Space;
import clueless.pieces.IPiece;
import clueless.pieces.PieceType;
import clueless.pieces.WeaponPiece;

import java.util.List;

public class TakeCommand extends Command {
    private final IPiece piece;

    public TakeCommand(Player player, Space space, IPiece piece){
        super(CommandType.TAKE, player, space);
        this.piece = piece;
    }

    private boolean isValidPieceToTake(PieceType type) {
        return type == PieceType.Weapon || type.isArtifact();
    }

    private void optionToSwapPieceInHand(IPiece newPiece) {
        PieceType newPieceType = newPiece.getType();
        IPiece existing = player.getPieceOfType(newPieceType);
        System.out.println("You already have a " + newPieceType.name() + ": " + existing.getName());
        System.out.println("Would you like to trade it for " + piece.getName() + "?");
        System.out.println("1. Yes\n2. No");

        if (getUserInputChoice(List.of("Yes", "No")).equals("Yes")) {
            player.removePiece(existing);
            player.addPiece(piece);
            space.addPiece(existing); // put the old piece back in the room
            System.out.println("Traded " + existing.getName() + " for " + piece.getName() + ".");
        } else {
            System.out.println("Kept " + existing.getName() + ".");
        }
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
        if (!isValidPieceToTake(pieceType)) {
            return false;
        }

        if (player.hasPieceOfType(pieceType)) {

        }

        space.removePiece(piece);

        return true;
    }

    @Override
    public String optionString() {
        return piece.getName();
    }
}
