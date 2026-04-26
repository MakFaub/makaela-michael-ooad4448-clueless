package clueless.board;

import clueless.pieces.IPiece;
import clueless.pieces.PieceFactory;

import java.util.*;

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

    public List<IPiece> getAllAvailableWeaponPieces() {
        return getRooms().stream().flatMap(room -> room.getWeaponPieces().stream()).toList();
    }

    public Room getRoomBasedOnPiece(IPiece piece) {
        return getRooms().stream().filter(room -> room.contains(piece)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No room contains piece: " + piece.getName()));
    }

    public Space getSpaceBasedOnPiece(IPiece piece) {
        return spaces.stream().filter(space -> space.contains(piece)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No space contains piece: " + piece.getName()));
    }

    public static class Builder {
        private final List<Space> spaces = new ArrayList<>();
        private final Map<String, Space> board = new HashMap<>();
        private static final Random random = new Random();

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

        public Builder placePieces(PieceFactory pieceFactory) {
            List<Room> rooms = spaces.stream()
                    .filter(s -> s instanceof Room)
                    .map(s -> (Room) s)
                    .toList();

            String[] weaponNames = pieceFactory.getWeaponNames();
            for (int i = 0; i < weaponNames.length; i++) {
                Room room = rooms.get(i % rooms.size());
                room.addPiece(pieceFactory.createWeaponPiece(weaponNames[i]));
            }

            String[] suspectNames = pieceFactory.getSuspectNames();
            for (int i = 0; i < suspectNames.length; i++) {
                Room room = rooms.get(i % rooms.size());
                room.addPiece(pieceFactory.createSuspectPiece(suspectNames[i]));
            }

            rooms.get(random.nextInt(rooms.size())).addPiece(pieceFactory.createSummonArtifact());
            rooms.get(random.nextInt(rooms.size())).addPiece(pieceFactory.createTransportArtifact());
            rooms.get(random.nextInt(rooms.size())).addPiece(pieceFactory.createConcealmentArtifact());

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
