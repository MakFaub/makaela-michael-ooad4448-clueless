package clueless.commands;

import clueless.Player;
import clueless.board.Board;
import clueless.board.Room;
import clueless.pieces.SuspectPiece;
import clueless.pieces.TransportArtifact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransportCommandTest {

    private Board board;
    private Room roomA;
    private Room roomB;
    private Player player;
    private SuspectPiece playerPiece;
    private TransportArtifact transportArtifact;

    @BeforeEach
    void setUp() {
        board = new Board.Builder().createBasicTestBoard().build();
        roomA = (Room) board.getSpace("Room A");
        roomB = (Room) board.getSpace("Room B");

        playerPiece = new SuspectPiece("Miss Scarlet");
        player = new Player("Miss Scarlet");
        player.assignPlayerPiece(playerPiece);
        roomA.addPiece(playerPiece);

        transportArtifact = new TransportArtifact("Seven League Boots");
        player.takePiece(transportArtifact);
    }

    @Test
    void testTransportMovesPlayerToDesiredRoom() {
        TransportCommand command = new TransportCommand(player, roomA, roomB);
        command.execute();
        assertTrue(roomB.contains(playerPiece));
    }

    @Test
    void testTransportRemovesPlayerFromOriginalRoom() {
        TransportCommand command = new TransportCommand(player, roomA, roomB);
        command.execute();
        assertFalse(roomA.contains(playerPiece));
    }

    @Test
    void testTransportUsesTransportArtifact() {
        TransportCommand command = new TransportCommand(player, roomA, roomB);
        command.execute();
        assertFalse(player.hasTransportArtifact());
    }

    @Test
    void testOptionString() {
        TransportCommand command = new TransportCommand(player, roomA, roomB);
        assertEquals("Room B", command.optionString());
    }
}