package clueless.pieces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceFactoryTest {

    private final PieceFactory pieceFactory = new PieceFactory();

    @Test
    void testCreateSuspectPieceWithName() {
        Piece piece = pieceFactory.createSuspectPiece("Miss Scarlet");
        assertEquals("Miss Scarlet", piece.getName());
        assertTrue(piece.isType(PieceType.Suspect));
    }

    @Test
    void testCreateSuspectPieceWithoutName() {
        Piece piece = pieceFactory.createSuspectPiece();
        assertNotNull(piece.getName());
        assertTrue(java.util.Arrays.asList(PieceFactory.SUSPECT_NAMES).contains(piece.getName()));
        assertTrue(piece.isType(PieceType.Suspect));
    }

    @Test
    void testCreateWeaponPieceWithName() {
        Piece piece = pieceFactory.createWeaponPiece("Dagger");
        assertEquals("Dagger", piece.getName());
        assertTrue(piece.isType(PieceType.Weapon));
    }

    @Test
    void createWeaponPieceWithoutName() {
        Piece piece = pieceFactory.createWeaponPiece();
        assertNotNull(piece.getName());
        assertTrue(java.util.Arrays.asList(PieceFactory.WEAPON_NAMES).contains(piece.getName()));
        assertTrue(piece.isType(PieceType.Weapon));
    }

    @Test
    void testCreateSummonArtifactWithName() {
        Piece piece = pieceFactory.createSummonArtifact("Summoning Wand");
        assertEquals("Summoning Wand", piece.getName());
        assertTrue(piece.isType(PieceType.Summon));
    }

    @Test
    void createSummonArtifactWithoutName() {
        Piece piece = pieceFactory.createSummonArtifact();
        assertNotNull(piece.getName());
        assertTrue(java.util.Arrays.asList(PieceFactory.SUMMON_ARTIFACT_NAMES).contains(piece.getName()));
        assertTrue(piece.isType(PieceType.Summon));
    }

    @Test
    void testCreateConcealmentArtifactWithName() {
        Piece piece = pieceFactory.createConcealmentArtifact("Cloak of Invisibility");
        assertEquals("Cloak of Invisibility", piece.getName());
        assertTrue(piece.isType(PieceType.Concealment));
    }

    @Test
    void createConcealmentArtifactWithoutName() {
        Piece piece = pieceFactory.createConcealmentArtifact();
        assertNotNull(piece.getName());
        assertTrue(java.util.Arrays.asList(PieceFactory.CONCEALMENT_ARTIFACT_NAMES).contains(piece.getName()));
        assertTrue(piece.isType(PieceType.Concealment));
    }

    @Test
    void testCreateTransportArtifactWithName() {
        Piece piece = pieceFactory.createTransportArtifact("Seven League Boots");
        assertEquals("Seven League Boots", piece.getName());
        assertTrue(piece.isType(PieceType.Transport));
    }

    @Test
    void createTransportArtifactWithoutName() {
        Piece piece = pieceFactory.createTransportArtifact();
        assertNotNull(piece.getName());
        assertTrue(java.util.Arrays.asList(PieceFactory.TRANSPORT_ARTIFACT_NAMES).contains(piece.getName()));
        assertTrue(piece.isType(PieceType.Transport));
    }
}