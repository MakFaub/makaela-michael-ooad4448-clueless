package clueless.pieces;

import java.util.Random;

public class PieceFactory {
    private static final Random random = new Random();

    static final String[] SUSPECT_NAMES = new String[]{
            "Miss Scarlet", "Colonel Mustard", "Dr. Orchard", "Mr. Green",
            "Mrs. Peacock", "Professor Plum"
    };

    static final String[] WEAPON_NAMES = new String[]{
            "Candlestick", "Rope", "Dagger", "Lead Pipe", "Revolver", "Wrench"};

    static final String[] SUMMON_ARTIFACT_NAMES = new String[]{
            "Summoning Wand"};

    static final String[] TRANSPORT_ARTIFACT_NAMES = new String[]{
            "Seven League Boots", "Talaria Winged Sandals"};

    static final String[] CONCEALMENT_ARTIFACT_NAMES = new String[]{
            "Tarnhelm", "Helm of Darkness", "Cloak of Invisibility"};

    public Piece createSuspectPiece(String name) { return new SuspectPiece(name); }

    public Piece createSuspectPiece() {
        return createSuspectPiece(getRandomSuspectName());
    }

    private static String getRandomSuspectName() {
        return SUSPECT_NAMES[random.nextInt(SUSPECT_NAMES.length)];
    }

    public Piece createWeaponPiece(String name) {
        return new WeaponPiece(name);
    }

    public Piece createWeaponPiece() {
        return createWeaponPiece(getRandomWeaponName());
    }

    private static String getRandomWeaponName() {
        return WEAPON_NAMES[random.nextInt(WEAPON_NAMES.length)];
    }

    public Piece createSummonArtifact(String name) {
        return new SummonArtifact(name);
    }

    public Piece createSummonArtifact() {
        return createSummonArtifact(getRandomSummonArtifactName());
    }

    private static String getRandomSummonArtifactName() {
        return SUMMON_ARTIFACT_NAMES[random.nextInt(SUMMON_ARTIFACT_NAMES.length)];
    }

    public Piece createTransportArtifact(String name) {
        return new TransportArtifact(name);
    }

    public Piece createTransportArtifact() {
        return createTransportArtifact(getRandomTransportArtifactName());
    }

    private static String getRandomTransportArtifactName() {
        return TRANSPORT_ARTIFACT_NAMES[random.nextInt(TRANSPORT_ARTIFACT_NAMES.length)];
    }

    public Piece createConcealmentArtifact(String name) {
        return new ConcealmentArtifact(name);
    }

    public Piece createConcealmentArtifact() {
        return createConcealmentArtifact(getRandomConcealmentArtifactName());
    }

    private static String getRandomConcealmentArtifactName() {
        return CONCEALMENT_ARTIFACT_NAMES[random.nextInt(CONCEALMENT_ARTIFACT_NAMES.length)];
    }
}
