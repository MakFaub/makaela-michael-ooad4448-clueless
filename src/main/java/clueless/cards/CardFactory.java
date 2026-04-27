package clueless.cards;

import clueless.Player;
import clueless.board.Space;

import java.util.List;
import java.util.Random;

public class CardFactory {
    private static final Random random = new Random();

    private final CardList cards;

    public CardFactory() {
        this(CardList.STANDARD_CARDS);
    }

    public CardFactory(CardList cards) {
        this.cards = cards;
    }

    public CardList getCardList() {
        return cards;
    }

    public Card createSuspectCard(String name) { return new SuspectCard(name); }
    public Card createWeaponCard(String name) { return new WeaponCard(name); }
    public Card createRoomCard(String name) { return new RoomCard(name); }


    public Card createSuspectCard() { return createSuspectCard(getRandomSuspectName(cards.rooms())); }
    public Card createWeaponCard() { return createWeaponCard(getRandomWeaponName(cards.weapons())); }
    public Card createRoomCard() { return createRoomCard(getRandomRoomName(cards.rooms())); }


    private static String getRandomSuspectName(List<String> cards) { return cards.get(random.nextInt(cards.size())); }
    private static String getRandomWeaponName(List<String> cards) { return cards.get(random.nextInt(cards.size())); }
    private static String getRandomRoomName(List<String> cards) { return cards.get(random.nextInt(cards.size())); }

}
