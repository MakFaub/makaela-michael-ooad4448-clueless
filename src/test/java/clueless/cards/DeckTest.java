package clueless.cards;

import clueless.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    private CardFactory cardFactory;
    private Deck deck;

    private List<Player> makePlayers(int count) {
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            players.add(new Player("Player" + i));
        }
        return players;
    }

    @BeforeEach
    void setUp() {
        cardFactory = new CardFactory();
        deck = new Deck(cardFactory);
    }

    @Test
    void constructorLoadsAllStandardCards() {
        assertEquals(cardFactory.NUM_ROOMS, deck.getNumCardsInRoomDeck());
        assertEquals(cardFactory.NUM_SUSPECTS, deck.getNumCardsInSuspectDeck());
        assertEquals(cardFactory.NUM_WEAPONS, deck.getNumCardsInWeaponDeck());
    }

    @Test
    void dealWithNoPlayersThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> deck.deal(new ArrayList<>()));
    }

    @Test
    void afterDealEnvelopeContainsOneOfEachCardType() {
        List<Player> players = makePlayers(2);
        deck.deal(players);

        List<Card> envelopeCards = deck.getEnvelopeCards();

        assertEquals(3, envelopeCards.size());

        long numRooms = envelopeCards.stream().filter(card -> card instanceof RoomCard).count();
        long numSuspects = envelopeCards.stream().filter(card -> card instanceof SuspectCard).count();
        long numWeapons = envelopeCards.stream().filter(card -> card instanceof WeaponCard).count();

        assertEquals(1, numRooms);
        assertEquals(1, numSuspects);
        assertEquals(1, numWeapons);
    }

    // TODO: test player hands

    // TODO: test checkAgainstEnvelope
}
