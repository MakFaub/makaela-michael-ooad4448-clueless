package clueless.commands;

import clueless.Player;
import clueless.board.Board;
import clueless.board.Room;
import clueless.board.Space;
import clueless.pieces.SuspectPiece;
import clueless.pieces.WeaponPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SuggestCommandTest {

    static class FakeInputHandler implements IInputHandler {
        private final List<Object> answers;
        private int callCount = 0;

        FakeInputHandler(Object... answers) {
            this.answers = List.of(answers);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T choose(List<T> options) {
            return (T) answers.get(callCount++);
        }
    }

    private Board board;
    private Room kitchen;
    private Room ballroom;
    private Player player;
    private SuspectPiece suspectPiece;
    private WeaponPiece weaponInRoom;

    @BeforeEach
    void setUp() {
        board = new Board.Builder().createBasicBoard().build();
        kitchen = (Room) board.getSpace("Room A");
        ballroom = (Room) board.getSpace("Room B");

        SuspectPiece playerPiece = new SuspectPiece("Miss Scarlet");
        suspectPiece = new SuspectPiece("Colonel Mustard");
        weaponInRoom = new WeaponPiece("Candlestick");

        player = new Player("Miss Scarlet");
        player.assignPlayerPiece(playerPiece);

        kitchen.addPiece(playerPiece);
        ballroom.addPiece(suspectPiece);
        kitchen.addPiece(weaponInRoom);
    }

    @Test
    void testFailsWhenPlayerIsInHallway() {
        Space hallway = board.getSpace("hall AB");
        FakeInputHandler input = new FakeInputHandler();

        SuggestCommand command = new SuggestCommand(player, hallway, board, input);

        assertFalse(command.execute());
    }

    @Test
    void testSuspectIsMovedIntoCurrentRoomUponSuggestion() {
        FakeInputHandler input = new FakeInputHandler(weaponInRoom, suspectPiece);

        SuggestCommand command = new SuggestCommand(player, kitchen, board, input);

        assertTrue(command.execute());
        assertTrue(kitchen.contains(suspectPiece));
        assertFalse(ballroom.contains(suspectPiece));
    }

    @Test
    void testWeaponChoicesIncludeHeldWeapons() {
        WeaponPiece heldWeapon = new WeaponPiece("Knife");
        player.takePiece(heldWeapon);

        FakeInputHandler input = new FakeInputHandler(heldWeapon, suspectPiece);

        SuggestCommand command = new SuggestCommand(player, kitchen, board, input);

        assertTrue(command.execute());
        assertTrue(kitchen.contains(suspectPiece));
    }
}