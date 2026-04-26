package clueless.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import clueless.Player;

public class Deck {

    private final List<Card> roomDeck = new ArrayList<>();
    private final List<Card> suspectDeck = new ArrayList<>();
    private final List<Card> weaponDeck = new ArrayList<>();
    private boolean envelopeFilled = false;

    public Deck(CardFactory cardFactory) {
        for (String name : CardFactory.SUSPECT_NAMES) suspectDeck.add(cardFactory.createSuspectCard(name));

        for (String name : CardFactory.WEAPON_NAMES) weaponDeck.add(cardFactory.createWeaponCard(name));

        for (String name : CardFactory.ROOM_NAMES) roomDeck.add(cardFactory.createRoomCard(name));
    }

    public void shuffle() {
        Collections.shuffle(roomDeck);
        Collections.shuffle(suspectDeck);
        Collections.shuffle(weaponDeck);
    }

    public Envelope fillEnvelope() {
        if (envelopeFilled) throw new IllegalStateException("Envelope already filled.");
        if (roomDeck.isEmpty() || suspectDeck.isEmpty() || weaponDeck.isEmpty()) throw new IllegalStateException("decks are empty");

        envelopeFilled = true;
        return new Envelope(roomDeck.removeFirst(), suspectDeck.removeFirst(), weaponDeck.removeFirst());
    }

    public void deal(List<Player> players) {
        if(players.isEmpty()) throw new IllegalArgumentException("Cannot deal to an empty player list.");

        List<Card> remainingCards = new ArrayList<>();
        remainingCards.addAll(roomDeck);
        remainingCards.addAll(suspectDeck);
        remainingCards.addAll(weaponDeck);

        Collections.shuffle(remainingCards);

        for (int i = 0; i < remainingCards.size(); i++) {
            players.get(i % players.size()).addCardsToHand(List.of(remainingCards.get(i)));
        }
    }

    public int getNumCardsInRoomDeck() { return roomDeck.size(); }

    public int getNumCardsInSuspectDeck() { return suspectDeck.size(); }

    public int getNumCardsInWeaponDeck() { return weaponDeck.size(); }

    public List<Card> getSuspectDeck() { return suspectDeck; }

    public List<Card> getRoomDeck() { return roomDeck; }

    public List<Card> getWeaponDeck() { return weaponDeck; }
}
