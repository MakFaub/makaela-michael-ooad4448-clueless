package clueless.cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    void testGetCardName() {
        Card card = new RoomCard("Library");
        assertEquals("Library", card.getName());
    }

    @Test
    void testIsCardType() {
        Card card = new RoomCard("Library");
        assertTrue(card.isRoomCard());
        assertFalse(card.isSuspectCard());
        assertFalse(card.isWeaponCard());
    }
}
