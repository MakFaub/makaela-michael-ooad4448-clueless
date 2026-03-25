package clueless.board;

public enum NeighborDirection {
    NORTH, SOUTH, EAST, WEST, SECRET;

    //for neighbor bidirectionality
    public NeighborDirection opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST  -> WEST;
            case WEST  -> EAST;
            case SECRET  -> SECRET;
        };
    }
}
