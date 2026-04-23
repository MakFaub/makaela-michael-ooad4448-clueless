package clueless.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static clueless.board.Direction.*;

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

        public Builder(){}

        public Builder createBasicBoard() {
            Room roomA = new Room("Room A");
            Room roomB = new Room("Room B");
            Room roomC = new Room("Room C");
            Room roomD = new Room("Room D");

            Hallway hall_AB = new Hallway("hall AB");
            Hallway hall_BC = new Hallway("hall BC");
            Hallway hall_CD = new Hallway("hall CD");
            Hallway hall_DA = new Hallway("hall DA");

            roomA.connect(EAST, hall_AB);
            hall_AB.connect(EAST, roomB);

            roomB.connect(SOUTH, hall_BC);
            hall_BC.connect(SOUTH, roomC);

            roomC.connect(WEST, hall_CD);
            hall_CD.connect(WEST, roomD);

            roomD.connect(NORTH, hall_DA);
            hall_DA.connect(NORTH, roomA);

            roomA.connect(SECRET, roomC);
            roomB.connect(SECRET, roomD);

            addSpace(roomA,roomB,roomC,roomD,hall_AB,hall_BC,hall_CD,hall_DA);

            return this;
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
            addSpaces(List.of(newSpaces));
        }

        private void addSpaces(List<Space> newSpaces) {
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
