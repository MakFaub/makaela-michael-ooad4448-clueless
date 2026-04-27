package clueless.commands;

import clueless.Player;
import clueless.board.Board;
import clueless.board.Room;
import clueless.board.Space;
import clueless.cards.Envelope;
import clueless.cards.RoomCard;
import clueless.cards.SuspectCard;
import clueless.cards.WeaponCard;
import clueless.pieces.SuspectPiece;
import clueless.pieces.WeaponPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryTest {

    private final CommandFactory factory = new CommandFactory();

    private Board board;
    private Room roomA;
    private Room roomB;
    private Space hallway;
    private Player player;
    private Player otherPlayer;
    private SuspectPiece playerPiece;
    private WeaponPiece weapon;
    private Envelope envelope;
    private IInputHandler fakeInputHandler;

    @BeforeEach
    void setUp() {
        board = new Board.Builder().createBasicBoard().build();
        roomA = (Room) board.getSpace("Room A");
        roomB = (Room) board.getSpace("Room B");
        hallway = board.getSpace("hall AB");

        playerPiece = new SuspectPiece("Miss Scarlet");
        player = new Player("Miss Scarlet");
        player.assignPlayerPiece(playerPiece);

        otherPlayer = new Player("Colonel Mustard");

        weapon = new WeaponPiece("Candlestick");
        roomA.addPiece(weapon);

        envelope = new Envelope(
                new RoomCard("Room A"),
                new SuspectCard("Colonel Mustard"),
                new WeaponCard("Candlestick")
        );

        fakeInputHandler = new IInputHandler() {
            @Override
            public <T> T choose(List<T> options) {
                return options.getFirst();
            }
        };
    }

    @Test
    void testNewMoveCommand() {
        assertEquals(CommandType.MOVE, factory.newMoveCommand(player, roomA, hallway).getType());
    }

    @Test
    void testNewSuggestCommand() {
        assertEquals(CommandType.SUGGEST, factory.newSuggestCommand(player, roomA, board, List.of(player), fakeInputHandler).getType());
    }

    @Test
    void testNewAccuseCommand() {
        assertEquals(CommandType.ACCUSE, factory.newAccuseCommand(player, roomA, board, envelope, List.of(player), fakeInputHandler).getType());
    }

    @Test
    void testNewLookCommand() {
        assertEquals(CommandType.LOOK, factory.newLookCommand(player, otherPlayer, roomA).getType());
    }

    @Test
    void testNewSummonCommand() {
        assertEquals(CommandType.SUMMON, factory.newSummonCommand(player, roomA, weapon, roomA).getType());
    }

    @Test
    void testNewTransportCommand() {
        assertEquals(CommandType.TRANSPORT, factory.newTransportCommand(player, roomA, roomB).getType());
    }

    @Test
    void testNewTakeCommand() {
        assertEquals(CommandType.TAKE, factory.newTakeCommand(player, roomA, weapon, fakeInputHandler).getType());
    }
}