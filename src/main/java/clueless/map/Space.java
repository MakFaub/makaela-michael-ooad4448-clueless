package clueless.map;

import java.util.ArrayList;
import java.util.List;

public abstract class Space {
    final String name;
    final List<Space> neighbors = new ArrayList<>();

    public Space(String name){
        this.name = name;
    }

    public Space(String name, List<Space> neighbors) {
        this.name = name;
        addNeighbors(neighbors);
    }

    public void addNeighbor(Space neighbor) { if(!neighbors.contains(neighbor)) { neighbors.add(neighbor); } }

    public void addNeighbors(List<Space> neighbors){
        this.neighbors.addAll(neighbors);
    }

    public List<Space> getNeighbors(){
        return List.copyOf(neighbors);
    }

    public String getName(){
        return name;
    }
}
