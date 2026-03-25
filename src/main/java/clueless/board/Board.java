package clueless.map;

import java.util.List;

public class Board {
    private final List<Space> spaces;

    private Board(List<Space> spaces){
        this.spaces = spaces;


    }

    public List<Space> getSpaces() {
        return List.copyOf(spaces);
    }

    public Space getSpace(String name){
        return spaces.stream().filter(space -> space.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No space with name: " + name));
    }
}
