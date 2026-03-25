package clueless.board;

import java.util.List;

public class Room extends Space{

    private Room secretPassage;

    public Room(String name){
        super(name);
    }

    public boolean isUnoccupied(Space space){ return space instanceof Hallway hallway && !hallway.isOccupied(); }

    public void addSecretPassage(Room secretNeighbor){
        if (secretNeighbor == null || secretNeighbor == this) {throw new IllegalArgumentException("Secret passage target cannot be null.");}

        this.secretPassage = secretNeighbor;

        this.addNeighbor(NeighborDirection.SECRET, secretNeighbor);
    }

    public Room getSecretPassage(){return this.secretPassage;}

    public List<Space> getValidRoomExits() {
        return getNeighbors().values().stream()
                .filter(this::isUnoccupied)
                .toList();
    }
}
