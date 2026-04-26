package clueless.commands;

import clueless.Player;
import clueless.board.Room;
import clueless.board.Space;

public class TransportCommand extends Command {
    private final Room room;

    public TransportCommand(Player myself, Space space, Room room){
        super(CommandType.TRANSPORT, myself, space);
        this.room = room;
    }

    @Override
    public boolean execute() {
        if (!player.hasTransportArtifact()) {
            System.out.println(player.getName() + "does not have a transport artifact and cannot move to room " + room.getName());
            return false;
        }

        room.addPiece(player.getPlayerPiece());
        space.removePiece(player.getPlayerPiece());

        return true;
    }

    @Override
    public String optionString() { return room.getName(); }
}