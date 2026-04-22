package clueless.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import clueless.Player;

public class Deck {

    private final List<RoomCard> roomDeck = new ArrayList<>();
    private final List<SuspectCard> suspectDeck = new ArrayList<>();
    private final List<WeaponCard> weaponDeck = new ArrayList<>();

    private final List<Card> envelopeCards = new ArrayList<>(); // secret cards players are trying to guess

    public static Deck buildStandardDeck(CardFactory cardFactory) {
        Deck deck = new Deck();

        for (String name : CardFactory.SUSPECT_NAMES) {
            deck.suspectDeck.add((SuspectCard) cardFactory.createSuspectCard(name));
        }

        for (String name : CardFactory.WEAPON_NAMES) {
            deck.weaponDeck.add((WeaponCard) cardFactory.createWeaponCard(name));
        }

        for (String name : CardFactory.ROOM_NAMES) {
            deck.roomDeck.add((RoomCard) cardFactory.createRoomCard(name));
        }

        return deck;
    }

    private void shuffle() {
        Collections.shuffle(roomDeck);
        Collections.shuffle(suspectDeck);
        Collections.shuffle(weaponDeck);
    }

    public void deal(List<Player> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("Cannot deal to an empty player list.");
        }

        this.shuffle();

        // TODO: still working on this

    }


}
