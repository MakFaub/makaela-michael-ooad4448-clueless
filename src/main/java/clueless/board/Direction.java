package clueless.board;

public enum Direction {
    NORTH, SOUTH, EAST, WEST, SECRET;

    //for neighbor bidirectionality
    public Direction opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST  -> WEST;
            case WEST  -> EAST;
            case SECRET  -> SECRET;
        };
    }
}
