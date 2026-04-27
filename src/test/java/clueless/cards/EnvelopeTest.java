package clueless.cards;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;




public class EnvelopeTest {
    private CardFactory cardFactory;
    private Card roomCard;
    private Card suspectCard;
    private Card weaponCard;
    private Envelope envelope;

    @BeforeEach
    void setUp() {
        cardFactory = new CardFactory();
        roomCard = cardFactory.createRoomCard("Kitchen");
        suspectCard = cardFactory.createSuspectCard("Miss Scarlet");
        weaponCard = cardFactory.createWeaponCard("Candlestick");
        envelope = new Envelope(roomCard,suspectCard,weaponCard);
    }

    @Test
    void testGetCards() {
        assertEquals(3, envelope.getCards().size());
        assertTrue(envelope.getCards().contains(roomCard));
        assertTrue(envelope.getCards().contains(suspectCard));
        assertTrue(envelope.getCards().contains(weaponCard));
    }

    @Test
    void testGetters() {
        assertEquals(roomCard, envelope.roomCard());
        assertEquals(suspectCard, envelope.suspectCard());
        assertEquals(weaponCard, envelope.weaponCard());
    }

    @Test
    void testCheckEnvelopeWhenTrue() {
        roomCard = cardFactory.createRoomCard("Kitchen");
        suspectCard = cardFactory.createSuspectCard("Miss Scarlet");
        weaponCard = cardFactory.createWeaponCard("Candlestick");
        assertTrue(envelope.checkGuess(roomCard, suspectCard, weaponCard));
    }

    @Test
    void testCheckEnvelopeWhenFalse() {
        roomCard = cardFactory.createRoomCard("Library");
        suspectCard = cardFactory.createSuspectCard("Miss Scarlet");
        weaponCard = cardFactory.createWeaponCard("Candlestick");
        assertFalse(envelope.checkGuess(roomCard, suspectCard, weaponCard));
    }
}
