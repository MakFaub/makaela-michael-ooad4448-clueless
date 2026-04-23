package clueless.board;

import clueless.pieces.Piece;

public class Hallway extends Space {
    public Hallway(String name) {
        super(name);
    }

    @Override
    public boolean isOccupied() {
        return !getSuspectPieces().isEmpty();
    }

    @Override
    public boolean enter(Piece piece) {
        if(isOccupied()){ return false; }
        else{
            super.enter(piece);
            return true;
        }
    }

    @Override
    public void connect(Direction direction, Space neighbor){
        if(direction == Direction.SECRET){
            throw new IllegalArgumentException("Hallways cannot use Direction SECRET.");
        }
        super.connect(direction, neighbor);
    }

    @Override
    public boolean isHallway() { return true ;}
}