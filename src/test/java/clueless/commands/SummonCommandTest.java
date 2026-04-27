package clueless.commands;

import clueless.Player;
import clueless.board.Board;
import clueless.board.Room;
import clueless.pieces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SummonCommandTest {

    private Board board;
    private Room kitchen;
    private Room ballroom;
    private Player player;
    private SuspectPiece playerPiece;
    private WeaponPiece weapon;
    private SummonArtifact summonArtifact;

    @BeforeEach
    void setUp() {
        board = new Board.Builder().createBasicBoard().build();
        kitchen = (Room) board.getSpace("Room A");
        ballroom = (Room) board.getSpace("Room B");

        playerPiece = new SuspectPiece("Miss Scarlet");
        player = new Player("Miss Scarlet");
        player.assignPlayerPiece(playerPiece);
        kitchen.addPiece(playerPiece);

        weapon = new WeaponPiece("Candlestick");
        ballroom.addPiece(weapon);

        summonArtifact = new SummonArtifact("Summoning Wand");
        player.takePiece(summonArtifact);
    }

    // -------------------------------------------------------------------------
    // Guard clause: weapon not in room
    // -------------------------------------------------------------------------

    @Test
    void testExecuteReturnsFalseWhenWeaponIsNotInRoom() {
        ballroom.removePiece(weapon);
        SummonCommand command = new SummonCommand(player, kitchen, weapon, ballroom);
        assertFalse(command.execute());
        assertFalse(player.hasWeaponPiece());
        assertTrue(player.hasSummonArtifact());
    }

    @Test
    void testWeaponIsSummoned() {
        SummonCommand command = new SummonCommand(player, kitchen, weapon, ballroom);
        command.execute();
        assertTrue(player.hasWeaponPiece());
        assertFalse(ballroom.contains(weapon));
        assertFalse(player.hasSummonArtifact());
    }

    @Test
    void testWeaponSwap() {
        WeaponPiece oldWeapon = new WeaponPiece("Rope");
        player.takePiece(oldWeapon);

        SummonCommand command = new SummonCommand(player, kitchen, weapon, ballroom);
        command.execute();

        assertFalse(player.getPiecesInHand().contains(oldWeapon));
        assertTrue(player.getPiecesInHand().contains(weapon));
        assertTrue(player.getPiecesInHand().contains(weapon));
        assertTrue(ballroom.contains(oldWeapon));
        assertFalse(ballroom.contains(weapon));
    }

    @Test
    void testOptionString() {
        SummonCommand command = new SummonCommand(player, kitchen, weapon, ballroom);
        assertEquals("Candlestick", command.optionString());
    }
}