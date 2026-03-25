package clueless.board;

import clueless.Player;

import java.util.*;

public abstract class Space {
    private final String name;
    private final Map<NeighborDirection, Space> neighbors = new EnumMap<NeighborDirection, Space>(NeighborDirection.class);
    boolean startingSpace;

    final List<Player> players = new ArrayList<Player>();

    public Space(String name){
        this.name = name;
    }

    public Space(String name, Boolean isStartingSpace){
        this.name = name;
        this.startingSpace = isStartingSpace;
    }

    public boolean isStartingSpace(){
        return startingSpace;
    }

    public void setStartingSpace(boolean startingSpace){
        this.startingSpace = startingSpace;
    }

    public void addNeighbor(NeighborDirection direction, Space neighbor){
        if(neighbor != null){
            neighbors.put(direction, neighbor);
            neighbor.neighbors.put(direction.opposite(), this);
        }
    }

    public Space getNeighbor(NeighborDirection direction){
        return neighbors.get(direction);
    }

    public Map<NeighborDirection, Space> getNeighbors(){
        return Map.copyOf(neighbors);
    }

    public List<Player> getPlayers(){
        return List.copyOf(players);
    }

    public boolean isOccupied(){
        return !players.isEmpty();
    }

    public boolean enter(Player player){
        players.add(player);
        return true;
    }

    public boolean leave(Player player){
        players.remove(player);
        return true;
    }

    public String getName(){
        return name;
    }
}
