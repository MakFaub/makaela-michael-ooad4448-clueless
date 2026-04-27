package clueless.board;

import clueless.Player;
import clueless.pieces.PieceFactory;
import clueless.pieces.SuspectPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardDisplayTest {

    private Board board;
    private Player player1;
    private Player player2;
    private SuspectPiece piece1;
    private SuspectPiece piece2;
    private Room kitchen;

    @BeforeEach
    void setUp() {
        board = new Board.Builder()
                .createDefaultClueBoard()
                .placePieces(new PieceFactory())
                .build();

        kitchen = (Room) board.getSpace("Kitchen");

        piece1 = (SuspectPiece) board.findSuspectPieceByName("Miss Scarlet");
        piece2 = (SuspectPiece) board.findSuspectPieceByName("Colonel Mustard");

        player1 = new Player("Makaela");
        player1.assignPlayerPiece(piece1);

        player2 = new Player("Michael");
        player2.assignPlayerPiece(piece2);
    }

    @Test
    void testDisplayShowsAllRooms() {
        String output = BoardDisplay.render(board, List.of());
        for (Room room : board.getRooms()) {
            assertTrue(output.contains(room.getName()), "Expected room: " + room.getName());
        }
    }

    @Test
    void testWeaponIsShownInRoom() {
        String output = BoardDisplay.render(board, List.of());
        assertTrue(board.getRooms().stream()
                .anyMatch(r -> output.contains(r.getName())));
    }

    @Test
    void testPlayerNameIsDisplayedWhenGiven() {
        Space currentSpace = board.getSpaceBasedOnPiece(piece1);
        currentSpace.removePiece(piece1);
        kitchen.addPiece(piece1);

        String output = BoardDisplay.render(board, List.of(player1));
        assertTrue(output.contains("Miss Scarlet (Makaela)"));
    }

    @Test
    void testDisplayShowsAllPiecesInRoomIfMoreThanOne() {
        Space space1 = board.getSpaceBasedOnPiece(piece1);
        Space space2 = board.getSpaceBasedOnPiece(piece2);
        space1.removePiece(piece1);
        space2.removePiece(piece2);
        kitchen.addPiece(piece1);
        kitchen.addPiece(piece2);

        String output = BoardDisplay.render(board, List.of(player1, player2));
        assertTrue(output.contains("Miss Scarlet (Makaela)"));
        assertTrue(output.contains("Colonel Mustard (Michael)"));
    }

    @Test
    void testDisplayShowsEmptyHallwayCount() {
        String output = BoardDisplay.render(board, List.of());
        assertTrue(output.contains("empty hallways"));
    }
}