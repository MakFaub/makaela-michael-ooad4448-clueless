package clueless.board;

import org.junit.jupiter.api.Test;

import static clueless.board.Direction.*;
import static org.junit.jupiter.api.Assertions.*;

import clueless.pieces.Piece;
import clueless.pieces.PieceFactory;
import clueless.pieces.SuspectPiece;

public class HallwayTest {
    private final PieceFactory factory = new PieceFactory();
    
    @Test
    void testGetName() {
        Hallway hallway = new Hallway("Hallway AB");
        assertEquals("Hallway AB", hallway.getName());
    }

    @Test
    void testConnect() {
        Hallway hallway = new Hallway("Hallway AB");
        Room room = new Room("Room A");
        hallway.connect(WEST, room);
        assertEquals(room, hallway.getNeighbor(WEST));
    }

    @Test
    void testConnectBidirectional() {
        Hallway hallway = new Hallway("Hallway AB");
        Room room = new Room("Room A");
        hallway.connect(WEST, room);
        assertEquals(hallway, room.getNeighbor(EAST));
    }

    @Test
    void testConnectSecretThrows() {
        Hallway hallway = new Hallway("Hallway AB");
        Room room = new Room("Room A");
        assertThrows(IllegalArgumentException.class, () -> hallway.connect(SECRET, room));
    }

    @Test
    void testSetStartingSpace() {
        Hallway hallway = new Hallway("Hallway AB");
        assertFalse(hallway.isStartingSpace());
        hallway.setStartingSpace(true);
        assertTrue(hallway.isStartingSpace());
    }

    @Test
    void testCreateStartingSpace() {
        Hallway hallway = new Hallway("hall", true);
        assertTrue(hallway.isStartingSpace());
    }
}
