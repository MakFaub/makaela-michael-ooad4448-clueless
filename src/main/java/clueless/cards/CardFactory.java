package clueless.cards;

import java.util.Random;

public class CardFactory {
    private static final Random random = new Random();

    private static final String[] ROOM_NAMES = new String[]{
            "Billiard Room", "Ballroom", "Kitchen", "Conservatory",
            "Dining Room", "Hall", "Library", "Lounge", "Study"
    };

    private static final String[] SUSPECT_NAMES = new String[]{
            "Miss Scarlet", "Colonel Mustard", "Dr. Orchard", "Mr. Green",
            "Mrs. Peacock", "Professor Plum"
    };

    private static final String[] WEAPON_NAMES = new String[]{
            "Candlestick", "Rope", "Dagger", "Lead Pipe", "Revolver", "Wrench"};

    public Card createRoomCard(String name) {
        return new RoomCard(name);
    }

    public Card createRoomCard() {
        return createRoomCard(getRandomRoomName());
    }

    private static String getRandomRoomName() {
        return ROOM_NAMES[random.nextInt(ROOM_NAMES.length)];
    }

    public Card createSuspectCard(String name) { return new SuspectCard(name); }

    public Card createSuspectCard() {
        return createSuspectCard(getRandomSuspectName());
    }

    private static String getRandomSuspectName() {
        return SUSPECT_NAMES[random.nextInt(SUSPECT_NAMES.length)];
    }

    public Card createWeaponCard(String name) {
        return new WeaponCard(name);
    }

    public Card createWeaponCard() {
        return createWeaponCard(getRandomWeaponName());
    }

    private static String getRandomWeaponName() {
        return WEAPON_NAMES[random.nextInt(WEAPON_NAMES.length)];
    }
}
