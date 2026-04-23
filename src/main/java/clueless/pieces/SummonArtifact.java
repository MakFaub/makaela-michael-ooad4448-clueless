package clueless.pieces;

public class SummonArtifact extends ArtifactPiece {
    SummonArtifact(String name){ super(PieceType.Summon, name); }

    public PieceType getType() { return PieceType.Summon; }

}
