package clueless.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static class Builder {
        private final List<Space> spaces = new ArrayList<>();
        private final Map<String, Space> board = new HashMap<>();
        private final int gridSize;

        public Builder(int gridSize) {
            this.gridSize = gridSize;
        }

        public Builder createDefaultClueBoard() {
            Room kitchen = new Room("Kitchen");
            Room ballroom = new Room("Ballroom");
            Room conservatory = new Room("Conservatory");
            Room billiard = new Room("Billiard Room");
            Room library = new Room("Library");
            Room study = new Room("Study");
            Room hall = new Room("Hall");
            Room lounge = new Room("Lounge");
            Room dining = new Room("Dining Room");

            addSpace(kitchen, ballroom, conservatory, billiard,  library, study, hall, lounge, dining);

            return this;
        }

        private void addSpace(Space... newSpaces) {
            for (Space space : newSpaces) {
                spaces.add(space);
                board.put(space.getName(), space);
            }
        }

        public Board build() {
            return new Board(spaces);
        }



    }
}
