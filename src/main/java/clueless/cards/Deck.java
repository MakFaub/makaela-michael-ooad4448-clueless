package clueless.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import clueless.Player;

public class Deck {

    private final List<Card> roomDeck = new ArrayList<>();
    private final List<Card> suspectDeck = new ArrayList<>();
    private final List<Card> weaponDeck = new ArrayList<>();

    private final List<Card> envelopeCards = new ArrayList<>(); // secret cards players are trying to guess

    public Deck(CardFactory cardFactory) {
        for (String name : CardFactory.SUSPECT_NAMES) {
            suspectDeck.add((SuspectCard) cardFactory.createSuspectCard(name));
        }

        for (String name : CardFactory.WEAPON_NAMES) {
            weaponDeck.add((WeaponCard) cardFactory.createWeaponCard(name));
        }

        for (String name : CardFactory.ROOM_NAMES) {
            roomDeck.add((RoomCard) cardFactory.createRoomCard(name));
        }
    }

    private void shuffle() {
        Collections.shuffle(roomDeck);
        Collections.shuffle(suspectDeck);
        Collections.shuffle(weaponDeck);
    }

    private List<Card> dealOneOfEachCardType() {
        List<Card> oneOfEach = new ArrayList<>();
        oneOfEach.add(roomDeck.getFirst());
        oneOfEach.add(suspectDeck.getFirst());
        oneOfEach.add(weaponDeck.getFirst());

        roomDeck.removeFirst();
        suspectDeck.removeFirst();
        weaponDeck.removeFirst();

        return oneOfEach;
    }

    public void deal(List<Player> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("Cannot deal to an empty player list.");
        }

        this.shuffle();

        envelopeCards.addAll(dealOneOfEachCardType());

        for (Player player : players) {
            player.addCardsToHand(dealOneOfEachCardType());
        }
    }

    public boolean checkGuessAgainstEnvelopeCards(List<Card> cards) {
        return cards.equals(envelopeCards);
    }

    public int getNumCardsInRoomDeck() { return roomDeck.size(); }

    public int getNumCardsInSuspectDeck() { return suspectDeck.size(); }

    public int getNumCardsInWeaponDeck() { return weaponDeck.size(); }

    public List<Card> getEnvelopeCards() { return envelopeCards; }
}
