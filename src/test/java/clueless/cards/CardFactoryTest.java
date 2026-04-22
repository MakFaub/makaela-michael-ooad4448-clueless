package clueless.cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardFactoryTest {

    private final CardFactory factory = new CardFactory();

    @Test

    void testCreateRoomCard() {
        Card card = factory.createRoomCard();

        assertNotNull(card);
        assertInstanceOf(RoomCard.class, card);
    }

    @Test
    void testCreateSuspectCard() {
        Card card = factory.createSuspectCard();

        assertNotNull(card);
        assertInstanceOf(SuspectCard.class, card);
    }

    @Test
    void testCreateWeaponCard() {
        Card card = factory.createWeaponCard();

        assertNotNull(card);
        assertInstanceOf(WeaponCard.class, card);
    }
}
