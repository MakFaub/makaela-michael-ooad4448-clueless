package clueless.cards;

import clueless.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    private CardFactory cardFactory;
    private Deck deck;

    @BeforeEach
    void setUp() {
        cardFactory = new CardFactory();
        deck = new Deck(cardFactory);
    }

    @Test
    void testDeckCreation() {
        assertEquals(9, deck.getNumCardsInRoomDeck());
        assertEquals(6, deck.getNumCardsInSuspectDeck());
        assertEquals(6, deck.getNumCardsInWeaponDeck());
    }

    @Test
    void testShuffle() {
        Deck unshuffled = new Deck(cardFactory);
        deck.shuffle();
        assertNotEquals(deck.getSuspectDeck(), unshuffled.getSuspectDeck());
        assertNotEquals(deck.getWeaponDeck(), unshuffled.getWeaponDeck());
        assertNotEquals(deck.getRoomDeck(), unshuffled.getRoomDeck());
    }

    @Test
    void testFillEnvelope() {
        Envelope envelope = deck.fillEnvelope();
        assertTrue(envelope.roomCard().isRoomCard());
        assertTrue(envelope.suspectCard().isSuspectCard());
        assertTrue(envelope.weaponCard().isWeaponCard());

        assertFalse(deck.getRoomDeck().contains(envelope.roomCard()));
        assertFalse(deck.getSuspectDeck().contains(envelope.suspectCard()));
        assertFalse(deck.getWeaponDeck().contains(envelope.weaponCard()));
    }

    @Test
    void testDeal() {
        deck.fillEnvelope();
        Player scarlett = new Player("Scarlett");
        Player mustard =  new Player("Mustard");
        List<Player> players = List.of(scarlett, mustard);
        deck.deal(players);
        int numCardsDealt = deck.getNumCardsInRoomDeck()+deck.getNumCardsInSuspectDeck()+deck.getNumCardsInWeaponDeck();
        int numCardsInHands = scarlett.getHand().size() + mustard.getHand().size();
        assertEquals(numCardsDealt, numCardsInHands);
    }
}
