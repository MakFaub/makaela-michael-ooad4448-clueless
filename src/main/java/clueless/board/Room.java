package clueless.board;

import java.util.List;

public class Room extends Space{

    private Room secretPassage;

    public Room(String name){
        super(name);
    }

    public Room getSecretPassage(){return this.secretPassage;}

    public void addSecretPassage(Room secretNeighbor){
        this.secretPassage = secretNeighbor;
        super.connect(Direction.SECRET, secretNeighbor);
    }

    @Override
    public void connect(Direction direction, Space neighbor){
        if(direction != Direction.SECRET){
            super.connect(direction, neighbor);
        } else {
            if(neighbor instanceof Room){ addSecretPassage((Room) neighbor); }
            else { throw new IllegalArgumentException("cannot use direction SECRET with hallways"); }
        }

    }

    public List<Space> getAvailableExits() {
        return getNeighbors().values().stream()
                .filter(Space::isAvailable)
                .toList();
    }
}
