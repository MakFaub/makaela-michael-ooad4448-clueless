package clueless.cards;

import clueless.board.Space;
import clueless.pieces.IPiece;

public abstract class Card {
    private final String name;

    Card(String name){ this.name = name; }

    public String getName(){ return name; }

    public boolean isRoomCard(){ return false; }

    public boolean isSuspectCard(){ return false; }

    public boolean isWeaponCard(){ return false; }

    public boolean matches(IPiece piece) {
        return this.name.equalsIgnoreCase(piece.getName());
    }

    public boolean matches(Space space) {
        return this.name.equalsIgnoreCase(space.getName());
    }
}
