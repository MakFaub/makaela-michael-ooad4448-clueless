package clueless.commands;

import clueless.Player;
import clueless.board.Space;
import clueless.pieces.IPiece;

public class MoveCommand extends Command {
    private final Space newSpace;
    private final IPiece playerPiece;

    public MoveCommand(Player myself, Space space, Space newSpace) {
        super(CommandType.MOVE, myself, space);
        this.newSpace = newSpace;
        this.playerPiece = player.getPlayerPiece();
    }


    // TODO: implement logger instead of system prints
    @Override
    public boolean execute() {
        if (!space.contains(playerPiece)) {
            System.out.println(player.getName() + "tried to exit a space they were not in.");
            return false;
        }
        if (!space.hasNeighbor(newSpace)) {
            System.out.println(player.getName() + "tried to move to a space that is not connected to their current location.");
            return false;
        }

        newSpace.addPiece(playerPiece);
        space.removePiece(playerPiece);

        return true;
    }

    @Override
    public String optionString() {
        return newSpace.getName();
    }
}