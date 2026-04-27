package clueless.commands;

import clueless.Player;
import clueless.board.Room;
import clueless.board.Space;
import clueless.cards.Card;
import clueless.cards.WeaponCard;
import clueless.pieces.ConcealmentArtifact;
import clueless.pieces.SuspectPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LookCommandTest {

    private Player myself;
    private Player otherPlayer;
    private Space space;
    private ConcealmentArtifact concealmentArtifact;

    @BeforeEach
    void setUp() {
        space = new Room("Kitchen");

        myself = new Player("Miss Scarlet");
        myself.assignPlayerPiece(new SuspectPiece("Miss Scarlet"));

        otherPlayer = new Player("Colonel Mustard");
        otherPlayer.assignPlayerPiece(new SuspectPiece("Colonel Mustard"));

        concealmentArtifact = new ConcealmentArtifact("Cloak of Invisibility");
    }

    @Test
    void testExecuteReturnsFalseWhenPlayerDoesNotHaveConcealmentArtifact() {
        LookCommand command = new LookCommand(myself, otherPlayer, space);
        assertFalse(command.execute());
    }

    @Test
    void testExecuteReturnsTrueWhenPlayerSuccessfullyLooksAtAnotherCard() {
        myself.takePiece(concealmentArtifact);
        otherPlayer.addCardsToHand(List.of(new WeaponCard("Candlestick")));

        LookCommand command = new LookCommand(myself, otherPlayer, space);
        assertTrue(command.execute());
    }

    @Test
    void testPeekedCardIsAddedToDiscoveredCards() {
        Card card = new WeaponCard("Candlestick");
        myself.takePiece(concealmentArtifact);
        otherPlayer.addCardsToHand(List.of(card));

        LookCommand command = new LookCommand(myself, otherPlayer, space);
        command.execute();

        assertTrue(myself.getDiscoveredCards().contains(card));
    }

    @Test
    void testConcealmentArtifactIsRemovedFromHandAfterUse() {
        myself.takePiece(concealmentArtifact);
        otherPlayer.addCardsToHand(List.of(new WeaponCard("Candlestick")));

        LookCommand command = new LookCommand(myself, otherPlayer, space);
        command.execute();

        assertFalse(myself.hasConcealmentArtifact());
    }

    @Test
    void testOptionString() {
        LookCommand command = new LookCommand(myself, otherPlayer, space);
        assertEquals("Colonel Mustard", command.optionString());
    }
}