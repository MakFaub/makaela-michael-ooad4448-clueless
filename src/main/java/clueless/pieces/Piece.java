package clueless.pieces;

public abstract class Piece implements IPiece {
    private final String name;
    private final PieceType pieceType;

    Piece(PieceType type, String name) {
        this.pieceType = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isType(PieceType type) {
        return this.pieceType == type;
    }
}
