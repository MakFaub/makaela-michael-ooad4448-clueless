package clueless.cards;

import java.util.List;

public record CardList(List<String> suspects, List<String> weapons, List<String> rooms) {
    static final String[] ROOM_NAMES = new String[]{
            "Billiard Room", "Ballroom", "Kitchen", "Conservatory",
            "Dining Room", "Hall", "Library", "Lounge", "Study"
    };

    static final String[] SUSPECT_NAMES = new String[]{
            "Miss Scarlet", "Colonel Mustard", "Dr. Orchard", "Mr. Green",
            "Mrs. Peacock", "Professor Plum"
    };

    static final String[] WEAPON_NAMES = new String[]{
            "Candlestick", "Rope", "Dagger", "Lead Pipe", "Revolver", "Wrench"
    };

    public static final CardList STANDARD_CARDS = new CardList(List.of(SUSPECT_NAMES), List.of(WEAPON_NAMES), List.of(ROOM_NAMES));
}
