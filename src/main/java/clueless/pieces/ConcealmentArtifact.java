package clueless.pieces;

public class ConcealmentArtifact extends ArtifactPiece {
    public ConcealmentArtifact(String name){ super(PieceType.Conceal, name); }

    public PieceType getType() { return PieceType.Conceal; }

}
