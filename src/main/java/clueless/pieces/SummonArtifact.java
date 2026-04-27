package clueless.pieces;

public class SummonArtifact extends ArtifactPiece {
    public SummonArtifact(String name){ super(PieceType.Summon, name); }

    public PieceType getType() { return PieceType.Summon; }

}
