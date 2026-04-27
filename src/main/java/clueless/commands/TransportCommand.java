package clueless.commands;

import clueless.EventBus;
import clueless.Player;
import clueless.board.Room;
import clueless.board.Space;
import clueless.observers.EventType;
import clueless.pieces.IPiece;
import clueless.pieces.PieceType;

import java.util.logging.Logger;

public class TransportCommand extends Command {
    private static final Logger logger = Logger.getLogger(TransportCommand.class.getName());

    private final Room room;

    public TransportCommand(Player myself, Space space, Room room){
        super(CommandType.TRANSPORT, myself, space);
        this.room = room;
    }

    @Override
    public boolean execute() {
        if (!player.hasTransportArtifact()) {
            logger.warning(player.getName() + " does not have a transport artifact and cannot move to room " + room.getName());
            return false;
        }

        room.addPiece(player.getPlayerPiece());
        space.removePiece(player.getPlayerPiece());

        IPiece transportArtifact = player.getPieceOfType(PieceType.Transport);
        player.removePiece(transportArtifact);
        EventBus.getInstance().postEvent(EventType.ARTIFACT_USED, transportArtifact.getName());

        logger.info(player.getName() + " used the " + transportArtifact.getName() + " to move from " + space.getName() + " to " + room.getName());

        return true;
    }

    @Override
    public String optionString() { return room.getName(); }
}
