package clueless.board;

import clueless.Player;

import java.util.List;

public class Room extends Space{

    private Room secretPassage;

    public Room(String name){
        super(name);
    }

    public Room getSecretPassage(){return this.secretPassage;}

    public void addSecretPassage(Room secretNeighbor){
        if (secretNeighbor == null || secretNeighbor == this) {throw new IllegalArgumentException("Invalid secret passage room.");}

        this.secretPassage = secretNeighbor;

        this.addNeighbor(NeighborDirection.SECRET, secretNeighbor);
    }

    public boolean isUnoccupied(Space space){ return space instanceof Hallway hallway && !hallway.isOccupied(); }

    public List<Space> getUnoccupiedExits() {
        return getNeighbors().values().stream()
                .filter(this::isUnoccupied)
                .toList();
    }
}
