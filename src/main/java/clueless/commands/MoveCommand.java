package clueless.commands;

import clueless.Player;
import clueless.board.Space;
import clueless.pieces.IPiece;

import java.util.logging.Logger;

public class MoveCommand extends Command {
    private static final Logger logger = Logger.getLogger(MoveCommand.class.getName());


    private final Space newSpace;
    private final IPiece playerPiece;

    public MoveCommand(Player myself, Space space, Space newSpace) {
        super(CommandType.MOVE, myself, space);
        this.newSpace = newSpace;
        this.playerPiece = player.getPlayerPiece();
    }

    @Override
    public boolean execute() {
        if (!space.contains(playerPiece)) {
            logger.warning(player.getName() + "tried to exit a space they were not in.");
            return false;
        }
        if (!space.hasNeighbor(newSpace)) {
           logger.warning(player.getName() + "tried to move to a space that is not connected to their current location.");
           return false;
        }

        newSpace.addPiece(playerPiece);
        space.removePiece(playerPiece);
        logger.info(player.getName() + ", you moved from " + space.getName() + " to " + newSpace.getName());

        return true;
    }

    @Override
    public String optionString() {
        return newSpace.getName();
    }
}
