package clueless.pieces;

public interface IPiece {
    String getName();

    boolean isType(PieceType type);

    PieceType getType();
}

