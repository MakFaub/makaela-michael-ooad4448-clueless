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

    @Test
    void suspectSubDeckIsShuffledBeforeDeal() {
        Deck unshuffledDeck = new Deck(cardFactory);

        List<String> suspectDeckBeforeShuffle = unshuffledDeck.getSuspectDeck().stream()
                .map(Card::getName)
                .toList();
        List<String> roomDeckBeforeShuffle = unshuffledDeck.getRoomDeck().stream()
                .map(Card::getName)
                .toList();
        List<String> weaponDeckBeforeShuffle = unshuffledDeck.getWeaponDeck().stream()
                .map(Card::getName)
                .toList();

        boolean shuffleDetected = false;
        for (int i = 0; i < 5; i++) {
            Deck freshDeck = new Deck(cardFactory);
            freshDeck.deal(makePlayers(1));

            List<String> suspectDeckAfterShuffle = freshDeck.getSuspectDeck().stream()
                    .map(Card::getName)
                    .toList();
            List<String> roomDeckAfterShuffle = freshDeck.getRoomDeck().stream()
                    .map(Card::getName)
                    .toList();
            List<String> weaponDeckAfterShuffle = freshDeck.getWeaponDeck().stream()
                    .map(Card::getName)
                    .toList();

            if (!suspectDeckAfterShuffle.equals(suspectDeckBeforeShuffle) || !roomDeckAfterShuffle.equals(roomDeckBeforeShuffle) || !weaponDeckAfterShuffle.equals(weaponDeckBeforeShuffle)) {
                shuffleDetected = true;
                break;
            }
        }

        assertTrue(shuffleDetected);
    }

    @Test
    void playerHandsAreDifferentDoNotContainSameCards() {
        List<Player> players = makePlayers(4);
        deck.deal(players);

        List<Card> usedCards = new ArrayList<>();

        for (Player player : players) {
            List<Card> hand = player.getHand();
            for (Card card : hand) {
                assertFalse(usedCards.contains(card));
                usedCards.add(card);
            }
        }
    }

    @Test
    void afterDealEachPlayerHasOneOfEachCardType() {
        List<Player> players = makePlayers(3);
        deck.deal(players);

        for (Player player : players) {
            List<Card> hand = player.getHand();

            long numRooms = hand.stream().filter(card -> card instanceof RoomCard).count();
            long numSuspects = hand.stream().filter(card -> card instanceof SuspectCard).count();
            long numWeapons = hand.stream().filter(card -> card instanceof WeaponCard).count();

            assertEquals(1, numRooms);
            assertEquals(1, numSuspects);
            assertEquals(1, numWeapons);
        }
    }

    @Test
    void testIncorrectEnvelopeGuess() {
        List<Player> players = makePlayers(2);
        deck.deal(players);

        List<Card> guess = new ArrayList<>();

        guess.add(cardFactory.createRoomCard());
        guess.add(cardFactory.createSuspectCard());
        guess.add(cardFactory.createWeaponCard());

        assertFalse(deck.checkGuessAgainstEnvelopeCards(guess));
    }

    @Test
    void testCorrectEnvelopGuess() {
        List<Player> players = makePlayers(2);
        deck.deal(players);

        List<Card> guess = deck.getEnvelopeCards();

        assertTrue(deck.checkGuessAgainstEnvelopeCards(guess));
    }
}
