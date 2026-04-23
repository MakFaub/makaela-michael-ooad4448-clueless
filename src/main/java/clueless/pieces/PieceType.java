package clueless.pieces;

public enum PieceType {
    Suspect, Weapon, Conceal, Summon, Transport;

    public boolean isArtifact() {
        return this == Conceal || this == Summon || this == Transport;
    }
}
