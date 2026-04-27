package clueless.commands;

import clueless.Player;
import clueless.board.Board;
import clueless.board.Hallway;
import clueless.board.Room;
import clueless.pieces.SuspectPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveCommandTest {

    private Board board;
    private Room roomA;
    private Room roomB;
    private Hallway hallway;
    private Player player;
    private SuspectPiece playerPiece;

    @BeforeEach
    void setUp() {
        board = new Board.Builder().createBasicBoard().build();
        roomA = (Room) board.getSpace("Room A");
        roomB = (Room) board.getSpace("Room B");
        hallway = (Hallway) board.getSpace("hall AB");

        playerPiece = new SuspectPiece("Miss Scarlet");
        player = new Player("Miss Scarlet");
        player.assignPlayerPiece(playerPiece);

        roomA.addPiece(playerPiece);
    }

    @Test
    void testExecuteReturnsFalseWhenSpacesAreNotNeighbors() {
        MoveCommand command = new MoveCommand(player, roomA, roomB);
        assertFalse(command.execute());
    }

    @Test
    void execute_returnsTrue_onValidMove() {
        MoveCommand command = new MoveCommand(player, roomA, hallway);
        assertTrue(command.execute());
    }

    @Test
    void testExecuteMovesPlayerToNewSpace() {
        MoveCommand command = new MoveCommand(player, roomA, hallway);
        command.execute();
        assertTrue(hallway.contains(playerPiece));
        assertFalse(roomA.contains(playerPiece));
    }

    @Test
    void testMoveUsingSecretPassage() {
        Room roomC = (Room) board.getSpace("Room C");
        MoveCommand command = new MoveCommand(player, roomA, roomC);
        assertTrue(command.execute());
        assertTrue(roomC.contains(playerPiece));
        assertFalse(roomA.contains(playerPiece));
    }

    @Test
    void testOptionString() {
        MoveCommand command = new MoveCommand(player, roomA, hallway);
        assertEquals("hall AB", command.optionString());
    }
}