package clueless.commands;

import clueless.Player;
import clueless.board.Space;
import clueless.pieces.IPiece;
import clueless.pieces.PieceType;

import java.util.List;

public class TakeCommand extends Command {
    private final IPiece piece;
    private final IInputHandler inputHandler;

    public TakeCommand(Player player, Space space, IPiece piece, IInputHandler inputHandler){
        super(CommandType.TAKE, player, space);
        this.piece = piece;
        this.inputHandler = inputHandler;
    }

    private boolean isValidPieceToTake(PieceType type) {
        return type == PieceType.Weapon || type.isArtifact();
    }

    private void optionToSwapPieceInHand(IPiece newPiece) {
        PieceType newPieceType = newPiece.getType();
        IPiece existing = player.getPieceOfType(newPieceType);
        System.out.println("You already have a " + newPieceType.name() + ": " + existing.getName());
        System.out.println("Would you like to trade it for " + newPiece.getName() + "?");

        if (inputHandler.choose(List.of("Yes", "No")).equals("Yes")) {
            player.swapPiecesInHand(existing, newPiece);
            space.removePiece(newPiece);
            space.addPiece(existing);
            System.out.println("Traded " + existing.getName() + " for " + newPiece.getName() + ".");
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
            optionToSwapPieceInHand(piece);
            return true;
        }

        space.removePiece(piece);
        player.takePiece(piece); // <-- missing
        System.out.println("Took " + piece.getName() + ".");
        return true;
    }

    @Override
    public String optionString() {
        return piece.getName();
    }
}
