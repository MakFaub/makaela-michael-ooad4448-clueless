package clueless.board;

import clueless.pieces.Piece;
import clueless.pieces.PieceFactory;
import org.junit.jupiter.api.Disabled;
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
    void testSetStartingSpace() {
        Room room = new Room("Kitchen");
        assertFalse(room.isStartingSpace());
        room.setStartingSpace(true);
        assertTrue(room.isStartingSpace());
    }
}
