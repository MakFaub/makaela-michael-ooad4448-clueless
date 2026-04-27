package clueless.commands;

import clueless.Player;
import clueless.board.Space;
import clueless.pieces.IPiece;
import clueless.pieces.PieceType;

import java.util.List;
import java.util.logging.Logger;

public class TakeCommand extends Command {
    private static final Logger logger = Logger.getLogger(TakeCommand.class.getName());

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
            logger.info(player.getName() + " traded " + existing.getName() + " for " + newPiece.getName() + ".");
        } else {
            logger.info(player.getName() + " kept " + existing.getName() + ".");
        }
    }

    @Override
    public boolean execute() {
        if (!space.contains(player.getPlayerPiece())) {
            logger.warning(player.getName() + " is not in " + space.getName());
            return false;
        }
        if (!space.contains(piece)) {
            logger.warning(piece.getName() + " is not in " + space.getName());
            return false;
        }

        // player can only have 1 weapon and 1 artifact in hand
        PieceType pieceType = piece.getType();
        if (!isValidPieceToTake(pieceType)) {
            logger.warning("Cannot take. Invalid piece: " + piece.getName());
            return false;
        }

        if (player.hasPieceOfType(pieceType)) {
            optionToSwapPieceInHand(piece);
            return true;
        }

        space.removePiece(piece);
        player.takePiece(piece);
        logger.info(player.getName() + ", you took " + piece.getName() + ".");
        return true;
    }

    @Override
    public String optionString() {
        return piece.getName();
    }
}
