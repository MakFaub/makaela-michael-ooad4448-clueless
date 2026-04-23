package clueless.board;

import clueless.pieces.Piece;
import clueless.pieces.PieceFactory;
import org.junit.jupiter.api.Test;

import static clueless.board.Direction.*;
import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {
    private final PieceFactory factory = new PieceFactory();

    @Test
    void testGetName() {
        Room room = new Room("Kitchen");
        assertEquals("Kitchen", room.getName());
    }

    @Test
    void testConnect() {
        Room room = new Room("Kitchen");
        Hallway hallway = new Hallway("Hallway AB");
        room.connect(EAST, hallway);
        assertEquals(hallway, room.getNeighbor(EAST));
    }

    @Test
    void testConnectBidirectional() {
        Room room = new Room("Kitchen");
        Hallway hallway = new Hallway("Hallway AB");
        room.connect(EAST, hallway);
        assertEquals(room, hallway.getNeighbor(WEST));
    }

    @Test
    void testSecretPassage() {
        Room roomA = new Room("Room A");
        Room roomC = new Room("Room C");
        roomA.connect(SECRET, roomC);
        assertEquals(roomC, roomA.getSecretPassage());
        assertEquals(roomC, roomA.getNeighbor(SECRET));
    }

    @Test
    void testNoSecretPassage() {
        Room room = new Room("Kitchen");
        assertNull(room.getSecretPassage());
    }

    @Test
    void testGetAvailableExits() {
        // exit exists
        Room room = new Room("Kitchen");
        Hallway hallway = new Hallway("Hallway AB");
        room.connect(EAST, hallway);
        assertEquals(1, room.getAvailableExits().size());
        assertTrue(room.getAvailableExits().contains(hallway));

        // exit is blocked by another player
        hallway.enter(factory.createSuspectPiece("Scarlett"));
        assertEquals(0, room.getAvailableExits().size());
        assertFalse(room.getAvailableExits().contains(hallway));
    }

    @Test
    void testEnter() {
        Room room = new Room("Kitchen");
        Piece player = factory.createSuspectPiece("Scarlett");
        room.enter(player);
        assertTrue(room.getSuspectPieces().contains(player));
    }

    @Test
    void testRoomAllowsMultiplePlayers() {
        Room room = new Room("Kitchen");
        room.enter(factory.createSuspectPiece("Scarlett"));
        room.enter(factory.createSuspectPiece("Mustard"));
        assertEquals(2, room.getSuspectPieces().size());
    }

    @Test
    void testLeave() {
        Room room = new Room("Kitchen");
        Piece player = factory.createSuspectPiece("Scarlett");
        room.enter(player);
        room.leave(player);
        assertFalse(room.getSuspectPieces().contains(player));
    }

    @Test
    void testIsOccupied() {
        Room room = new Room("Kitchen");
        assertFalse(room.isOccupied());
        room.enter(factory.createSuspectPiece("Scarlett"));
        assertTrue(room.isOccupied());
    }

    @Test
    void testCreateStartingSpace() {
        Room room = new Room("Kitchen", true);
        assertTrue(room.isStartingSpace());
    }

    @Test
    void testSetStartingSpace() {
        Room room = new Room("Kitchen");
        assertFalse(room.isStartingSpace());
        room.setStartingSpace(true);
        assertTrue(room.isStartingSpace());
    }

    @Test
    void testGetPieces() {
        Room room = new Room("Kitchen");
        Piece scarlet = factory.createSuspectPiece("Scarlet");
        Piece candleStick = factory.createWeaponPiece("CandleStick");
        room.enter(scarlet);
        room.enter(candleStick);
        assertEquals(2, room.getPieces().size());

        assertTrue(room.getPieces().contains(scarlet));
        assertTrue(room.getPieces().contains(candleStick));
    }

    @Test
    void testGetSuspectPieces() {
        Room room = new Room("Kitchen");
        Piece scarlet = factory.createSuspectPiece("Scarlet");
        Piece candleStick = factory.createWeaponPiece("CandleStick");
        room.enter(scarlet);
        room.enter(candleStick);
        assertEquals(1, room.getSuspectPieces().size());

        assertTrue(room.getPieces().contains(scarlet));
        assertTrue(room.getPieces().contains(candleStick));
    }


}
