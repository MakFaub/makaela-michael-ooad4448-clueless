package clueless.commands;

import clueless.Player;
import clueless.board.Board;
import clueless.board.Room;
import clueless.pieces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TakeCommandTest {

    static class FakeInputHandler implements IInputHandler {
        private final String answer;
        private boolean wasCalled = false;

        FakeInputHandler(String answer) { this.answer = answer; }
        FakeInputHandler() { this.answer = null; }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T choose(List<T> options) {
            wasCalled = true;
            return options.stream()
                    .filter(o -> o.equals(answer))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Answer not in options: " + answer));
        }

        boolean wasCalled() { return wasCalled; }
    }

    private Board board;
    private Room room;
    private Player player;
    private SuspectPiece playerPiece;
    private WeaponPiece weapon;
    private TransportArtifact artifact;

    @BeforeEach
    void setUp() {
        board = new Board.Builder().createBasicTestBoard().build();
        room = (Room) board.getSpace("Room A");

        playerPiece = new SuspectPiece("Miss Scarlet");
        player = new Player("Miss Scarlet");
        player.assignPlayerPiece(playerPiece);
        room.addPiece(playerPiece);

        weapon = new WeaponPiece("Candlestick");
        artifact = new TransportArtifact("Seven League Boots");
        room.addPiece(weapon);
        room.addPiece(artifact);
    }

    @Test
    void testExecuteReturnsFalseForInvalidPieceType() {
        SuspectPiece invalidPiece = new SuspectPiece("Colonel Mustard");
        room.addPiece(invalidPiece);
        TakeCommand command = new TakeCommand(player, room, invalidPiece, new FakeInputHandler());
        assertFalse(command.execute());
    }

    @Test
    void testAutoTakesWeaponIfPlayerDoesNotAlreadyHaveWeapon() {
        FakeInputHandler inputHandler = new FakeInputHandler();
        TakeCommand command = new TakeCommand(player, room, weapon, inputHandler);
        assertTrue(command.execute());
        assertTrue(player.hasWeaponPiece());
        assertFalse(room.contains(weapon));
        assertFalse(inputHandler.wasCalled());
    }

    @Test
    void testAcceptSwapWeapon() {
        WeaponPiece heldWeapon = new WeaponPiece("Rope");
        player.takePiece(heldWeapon);

        FakeInputHandler input = new FakeInputHandler("Yes");
        TakeCommand command = new TakeCommand(player, room, weapon, input);

        assertTrue(command.execute());
        assertTrue(input.wasCalled());
        assertTrue(player.getPiecesInHand().contains(weapon));
        assertFalse(player.getPiecesInHand().contains(heldWeapon));
        assertTrue(room.contains(heldWeapon));
        assertFalse(room.contains(weapon));
    }

    @Test
    void testDeclineSwapWeapon() {
        WeaponPiece heldWeapon = new WeaponPiece("Rope");
        player.takePiece(heldWeapon);

        FakeInputHandler input = new FakeInputHandler("No");
        TakeCommand command = new TakeCommand(player, room, weapon, input);

        assertTrue(command.execute());
        assertTrue(input.wasCalled());
        assertTrue(player.getPiecesInHand().contains(heldWeapon));
        assertFalse(player.getPiecesInHand().contains(weapon));
        assertTrue(room.contains(weapon));
    }

    @Test
    void testOptionString() {
        TakeCommand command = new TakeCommand(player, room, weapon, new FakeInputHandler());
        assertEquals("Candlestick", command.optionString());
    }
}