package clueless.pieces;

public abstract class Piece {
    private final String name;

    Piece(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
