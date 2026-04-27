package clueless.pieces;

public class TransportArtifact extends ArtifactPiece {
    public TransportArtifact(String name){ super(PieceType.Transport, name); }

    public PieceType getType() { return PieceType.Transport; }

}
