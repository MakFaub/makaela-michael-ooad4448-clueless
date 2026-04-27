package clueless.commands;

import clueless.Player;
import clueless.board.Board;
import clueless.board.Room;
import clueless.cards.*;
import clueless.pieces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccuseCommandTest {

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
    private Player player;
    private SuspectPiece playerPiece;
    private SuspectPiece suspectPiece;
    private WeaponPiece weaponPiece;
    private Room kitchen;
    private Envelope correctEnvelope;
    private List<Player> players;

    @BeforeEach
    void setUp() {
        board = new Board.Builder().createBasicTestBoard().build();
        kitchen = (Room) board.getSpace("Room A");

        playerPiece = new SuspectPiece("Miss Scarlet");
        suspectPiece = new SuspectPiece("Colonel Mustard");
        weaponPiece = new WeaponPiece("Candlestick");

        player = new Player("Miss Scarlet");
        player.assignPlayerPiece(playerPiece);

        kitchen.addPiece(playerPiece);
        kitchen.addPiece(suspectPiece);
        kitchen.addPiece(weaponPiece);

        players = List.of(player);

        correctEnvelope = new Envelope(
                new RoomCard("Room A"),
                new SuspectCard("Colonel Mustard"),
                new WeaponCard("Candlestick")
        );
    }

    private AccuseCommand command(Envelope envelope, IInputHandler input) {
        return new AccuseCommand(player, kitchen, board, envelope, players, input);
    }

    @Test
    void testExecuteReturnsTrueOnCorrectGuess() {
        FakeInputHandler input = new FakeInputHandler(weaponPiece, suspectPiece, kitchen);
        assertTrue(command(correctEnvelope, input).execute());
    }

    @Test
    void testExecuteReturnsFalseOnIncorrectGuess() {
        Envelope wrongEnvelope = new Envelope(
                new RoomCard("Room A"),
                new SuspectCard("Colonel Mustard"),
                new WeaponCard("Rope")
        );
        FakeInputHandler input = new FakeInputHandler(weaponPiece, suspectPiece, kitchen);
        assertFalse(command(wrongEnvelope, input).execute());
    }

    @Test
    void testOptionString() {
        FakeInputHandler input = new FakeInputHandler(weaponPiece, suspectPiece, kitchen);
        assertEquals("", command(correctEnvelope, input).optionString());
    }
}