package clueless.commands;

import clueless.Player;
import clueless.board.Board;
import clueless.board.Room;
import clueless.board.Space;
import clueless.cards.Card;
import clueless.cards.CardFactory;
import clueless.cards.Deck;
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

    private final CommandFactory commandFactory = new CommandFactory();

    private Board board;
    private Room roomA;
    private Room roomB;
    private Player player1;
    private Player player2;
    private Player player3;
    private List<Player> players;
    private SuspectPiece suspectPiece1;
    private SuspectPiece suspectPiece2;
    private WeaponPiece weaponInRoom;

    private ICommand suggest(Player player, Space space, List<Player> playerList, IInputHandler input) {
        return commandFactory.newSuggestCommand(player, space, board, playerList, input);
    }

    @BeforeEach
    void setUp() {
        board = new Board.Builder().createBasicTestBoard().build();
        roomA = (Room) board.getSpace("Room A");
        roomB = (Room) board.getSpace("Room B");

        player1 = new Player("Miss Scarlet");
        suspectPiece1 = new SuspectPiece("Miss Scarlet");
        player1.assignPlayerPiece(suspectPiece1);

        player2 = new Player("Colonel Mustard");
        player3 = new Player("Mrs. Peacock");
        players = List.of(player1, player2, player3);

        suspectPiece2 = new SuspectPiece("Colonel Mustard");
        weaponInRoom = new WeaponPiece("Candlestick");

        roomA.addPiece(suspectPiece1);
        roomB.addPiece(suspectPiece2);
        roomA.addPiece(weaponInRoom);

        Deck deck = new Deck(new CardFactory());
        deck.shuffle();
        deck.deal(players);
    }

    @Test
    void testFailsWhenPlayerIsInHallway() {
        Space hallway = board.getSpace("hall AB");
        assertFalse(suggest(player1, hallway, players, new FakeInputHandler()).execute());
    }

    @Test
    void testSuspectIsMovedIntoCurrentRoomUponSuggestion() {
        ICommand suggestCommand = suggest(player1, roomA, players, new FakeInputHandler(weaponInRoom, suspectPiece2));

        assertTrue(suggestCommand.execute());
        assertTrue(roomA.contains(suspectPiece2));
        assertFalse(roomB.contains(suspectPiece2));
    }

    @Test
    void testWeaponChoicesIncludeHeldWeapons() {
        WeaponPiece heldWeapon = new WeaponPiece("Knife");
        player1.takePiece(heldWeapon);

        assertTrue(suggest(player1, roomA, players, new FakeInputHandler(heldWeapon, suspectPiece2)).execute());
    }

    @Test
    void testPlayerLearnsCardWhenNextPlayerHasMatchingCard() {
        Card matchingCard = player2.getHand().stream().findFirst().orElseThrow();
        WeaponPiece matchingWeapon = new WeaponPiece(matchingCard.getName());
        roomA.addPiece(matchingWeapon);

        assertTrue(suggest(player1, roomA, players, new FakeInputHandler(matchingWeapon, suspectPiece2)).execute());
        assertTrue(player1.getDiscoveredCards().contains(matchingCard));
    }
}