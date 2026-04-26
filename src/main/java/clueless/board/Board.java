package clueless.board;

import clueless.pieces.IPiece;

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

    public List<Room> getRooms() {
        return spaces.stream().filter(space -> space instanceof Room).map(space -> (Room) space).toList();
    }

    public List<Hallway> getHallways() {
        return spaces.stream().filter(space -> space instanceof Hallway).map(space -> (Hallway) space).toList();
    }

    public List<Space> getStartingSpaces() {
        return spaces.stream().filter(Space::isStartingSpace).toList();
    }

    public List<Space> getNonStartingSpaces() {
        return spaces.stream().filter(s -> !s.isStartingSpace()).toList();
    }

    public Room getRoomBasedOnPiece(IPiece piece) {
        return getRooms().stream().filter(room -> room.contains(piece)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No room contains piece: " + piece.getName()));
    }

    public List<IPiece> getAllAvailableWeaponPieces() {
        return getRooms().stream().flatMap(room -> room.getWeaponPieces().stream()).toList();
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

            Hallway starting_1 = new Hallway("starting 1", true);
            Hallway starting_2 = new Hallway("starting 2", true);
            Hallway starting_3 = new Hallway("starting 3", true);
            Hallway starting_4 = new Hallway("starting 4", true);

            starting_1.connect(EAST, hall_DA);
            starting_2.connect(SOUTH, hall_AB);
            starting_3.connect(WEST, hall_BC);
            starting_4.connect(NORTH, hall_CD);

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

            addSpace(roomA,roomB,roomC,roomD,hall_AB,hall_BC,hall_CD,hall_DA,starting_1, starting_2,starting_3,starting_4);

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

            Map<String, Hallway> halls = new HashMap<>();
            for (int i = 1; i <= 24; i++) {
                String name = String.valueOf(i);
                halls.put(name, new Hallway(name));
            }

            Map<String, Hallway> starts = new HashMap<>();
            for (int i = 1; i <= 6; i++) {
                String name = "starting " + i;
                starts.put(name, new Hallway(name, true));
            }
            halls.values().forEach(this::addSpace);
            starts.values().forEach(this::addSpace);

            addSpace(kitchen, ballroom, conservatory, billiard,  library, study, hall, lounge, dining);

            halls.get("1").connect(WEST, starts.get("starting 1"));
            halls.get("1").connect(NORTH, study);
            halls.get("1").connect(EAST, halls.get("2"));

            halls.get("2").connect(EAST, halls.get("3"));

            halls.get("3").connect(NORTH, hall);
            halls.get("3").connect(EAST, halls.get("4"));

            halls.get("4").connect(EAST, halls.get("5"));

            halls.get("5").connect(NORTH, starts.get("starting 2"));
            halls.get("5").connect(EAST, halls.get("6"));

            halls.get("6").connect(EAST, halls.get("7"));

            halls.get("7").connect(NORTH, lounge);
            halls.get("7").connect(EAST, starts.get("starting 3"));
            halls.get("7").connect(SOUTH, halls.get("8"));

            halls.get("8").connect(SOUTH, halls.get("9"));

            halls.get("9").connect(EAST, dining);
            halls.get("9").connect(SOUTH, halls.get("10"));

            halls.get("10").connect(SOUTH, halls.get("11"));

            halls.get("11").connect(EAST, starts.get("starting 4"));
            halls.get("11").connect(SOUTH, halls.get("12"));

            halls.get("12").connect(SOUTH, halls.get("13"));

            halls.get("13").connect(EAST, kitchen);
            halls.get("13").connect(WEST, halls.get("14"));

            halls.get("14").connect(SOUTH, starts.get("starting 5"));
            halls.get("14").connect(WEST, halls.get("15"));

            halls.get("15").connect(WEST, halls.get("16"));

            halls.get("16").connect(SOUTH, ballroom);
            halls.get("16").connect(WEST, halls.get("17"));

            halls.get("17").connect(WEST, halls.get("18"));

            halls.get("18").connect(SOUTH, starts.get("starting 6"));
            halls.get("18").connect(WEST, halls.get("19"));

            halls.get("19").connect(WEST, conservatory);
            halls.get("19").connect(NORTH, halls.get("20"));

            halls.get("20").connect(NORTH, halls.get("21"));

            halls.get("21").connect(WEST, billiard);
            halls.get("21").connect(NORTH, halls.get("22"));

            halls.get("22").connect(NORTH, halls.get("23"));

            halls.get("23").connect(WEST, library);
            halls.get("23").connect(NORTH, halls.get("24"));

            halls.get("24").connect(NORTH, halls.get("1"));

            study.connect(SECRET, kitchen);
            lounge.connect(SECRET, conservatory);

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
