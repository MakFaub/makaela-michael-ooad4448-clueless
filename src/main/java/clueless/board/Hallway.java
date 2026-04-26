package clueless.board;


public class Hallway extends Space {
    public Hallway(String name) {
        super(name);
    }

    public Hallway(String name, boolean isStartingSpace) {
        super(name, isStartingSpace);
    }

    @Override
    public boolean isOccupied() {
        return !getSuspectPieces().isEmpty();
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