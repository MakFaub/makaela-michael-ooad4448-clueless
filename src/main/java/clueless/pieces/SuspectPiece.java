package clueless.pieces;

public class SuspectPiece extends Piece {
    SuspectPiece(String name){ super(PieceType.Suspect, name); }

    public PieceType getType() { return PieceType.Suspect; }
}
