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
    void testEnter() {
        Hallway hallway = new Hallway("Hallway AB");
        Piece player = factory.createSuspectPiece("Scarlett");
        assertTrue(hallway.enter(player));
        assertTrue(hallway.getSuspectPieces().contains(player));
    }

    @Test
    void testEnterWhenOccupied() {
        Hallway hallway = new Hallway("Hallway AB");
        Piece mustard = factory.createSuspectPiece("Mustard");
        hallway.enter(factory.createSuspectPiece("Scarlett"));
        assertFalse(hallway.enter(mustard));
        assertFalse(hallway.getSuspectPieces().contains(mustard));
    }

    @Test
    void testLeave() {
        Hallway hallway = new Hallway("Hallway AB");
        Piece player = factory.createSuspectPiece("Scarlett");
        hallway.enter(player);
        hallway.leave(player);
        assertFalse(hallway.getSuspectPieces().contains(player));
    }

    @Test
    void testIsOccupied() {
        Hallway hallway = new Hallway("Hallway AB");
        assertFalse(hallway.isOccupied());
        hallway.enter(factory.createSuspectPiece("Scarlett"));
        assertTrue(hallway.isOccupied());
    }

    @Test
    void testIsAvailable() {
        Hallway hallway = new Hallway("Hallway AB");
        assertTrue(hallway.isAvailable());
        hallway.enter(factory.createSuspectPiece("Scarlett"));
        assertFalse(hallway.isAvailable());
    }

    @Test
    void testSetStartingSpace() {
        Hallway hallway = new Hallway("Hallway AB");
        assertFalse(hallway.isStartingSpace());
        hallway.setStartingSpace(true);
        assertTrue(hallway.isStartingSpace());
    }
}
