package clueless.cards;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Deck {
    static private final Random rand = new Random();

    private final Set<RoomCard> roomDeck = new HashSet<>();
    private final Set<SuspectCard> suspectDeck = new HashSet<>();
    private final Set<WeaponCard> weaponDeck = new HashSet<>();


}
