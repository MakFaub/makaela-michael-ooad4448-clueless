package clueless.pieces;

public class ConcealmentArtifact extends ArtifactPiece {
    ConcealmentArtifact(String name){ super(PieceType.Concealment, name); }

    public PieceType getType() { return PieceType.Concealment; }

}
