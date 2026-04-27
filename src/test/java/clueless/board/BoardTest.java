package clueless.board;

import org.junit.jupiter.api.Test;

import static clueless.board.Direction.*;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    void testGetSpaces() {
        Board board = new Board.Builder().createBasicTestBoard().build();
        assertEquals(8, board.getSpaces().size());
    }

    @Test
    void testGetSpaceByName() {
        Board board = new Board.Builder().createBasicTestBoard().build();
        Space space = board.getSpace("Room A");
        assertEquals("Room A", space.getName());
    }

    @Test
    void testGetSpaceInvalidNameThrows() {
        Board board = new Board.Builder().createBasicTestBoard().build();
        assertThrows(IllegalArgumentException.class, () -> board.getSpace("Nonexistent"));
    }

    @Test
    void testRoomsConnectedViaHallways() {
        Board board = new Board.Builder().createBasicTestBoard().build();
        Space roomA = board.getSpace("Room A");
        Space hallAB = board.getSpace("hall AB");
        assertEquals(hallAB, roomA.getNeighbor(EAST));
    }

    @Test
    void testHallwayConnectedToRooms() {
        Board board = new Board.Builder().createBasicTestBoard().build();
        Space hallAB = board.getSpace("hall AB");
        Space roomA = board.getSpace("Room A");
        Space roomB = board.getSpace("Room B");
        assertEquals(roomA, hallAB.getNeighbor(WEST));
        assertEquals(roomB, hallAB.getNeighbor(EAST));
    }

    @Test
    void testSecretPassage() {
        Board board = new Board.Builder().createBasicTestBoard().build();
        Room roomA = (Room) board.getSpace("Room A");
        Room roomC = (Room) board.getSpace("Room C");
        assertEquals(roomC, roomA.getSecretPassage());
        assertEquals(roomA, roomC.getSecretPassage());
    }

    @Test
    void testBoardIsCyclic() {
        Board board = new Board.Builder().createBasicTestBoard().build();
        Space roomA = board.getSpace("Room A");
        Space hallAB = board.getSpace("hall AB");
        Space roomB = board.getSpace("Room B");
        Space hallBC = board.getSpace("hall BC");
        Space roomC = board.getSpace("Room C");
        Space hallCD = board.getSpace("hall CD");
        Space roomD = board.getSpace("Room D");
        Space hallDA = board.getSpace("hall DA");

        assertEquals(hallAB, roomA.getNeighbor(EAST));
        assertEquals(roomB, hallAB.getNeighbor(EAST));
        assertEquals(hallBC, roomB.getNeighbor(SOUTH));
        assertEquals(roomC, hallBC.getNeighbor(SOUTH));
        assertEquals(hallCD, roomC.getNeighbor(WEST));
        assertEquals(roomD, hallCD.getNeighbor(WEST));
        assertEquals(hallDA, roomD.getNeighbor(NORTH));
        assertEquals(roomA, hallDA.getNeighbor(NORTH));
    }



}
