package clueless.pieces;

public enum PieceType {
    Suspect, Weapon, Concealment, Summon, Transport;

    public boolean isArtifact() {
        return this == Concealment || this == Summon || this == Transport;
    }
}
