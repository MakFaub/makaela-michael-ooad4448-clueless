package clueless.pieces;

public class TransportArtifact extends ArtifactPiece {
    TransportArtifact(String name){ super(PieceType.Transport, name); }

    public PieceType getType() { return PieceType.Transport; }

}
