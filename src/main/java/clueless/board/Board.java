package clueless.board;

import clueless.pieces.IPiece;
import clueless.pieces.Piece;
import clueless.pieces.PieceFactory;

import java.util.*;

import static clueless.board.Direction.*;

public class Board {
    private final List<Space> spaces;

    // Default Clue Rooms
    public static final String KITCHEN = "Kitchen";
    public static final String BALLROOM = "Ballroom";
    public static final String CONSERVATORY = "Conservatory";
    public static final String BILLIARD_ROOM = "Billiard Room";
    public static final String LIBRARY = "Library";
    public static final String STUDY = "Study";
    public static final String HALL = "Hall";
    public static final String LOUNGE = "Lounge";
    public static final String DINING_ROOM = "Dining Room";

    // Default Hall Numbers
    public static final int DEFAULT_NUM_STARTING_HALLS = 6;
    public static final int DEFAULT_NUM_HALLS = 24;

    // Test Rooms
    public static final String ROOM_A = "Room A";
    public static final String ROOM_B = "Room B";
    public static final String ROOM_C = "Room C";
    public static final String ROOM_D = "Room D";
    public static final String ROOM_E = "Room E";
    public static final String ROOM_F = "Room F";

    public static final String STARTING_ROOM = "Starting ";

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

    public List<Hallway> getStartingHallways() {
        return spaces.stream()
                .filter(s -> s instanceof Hallway)
                .map(s -> (Hallway) s)
                .filter(Space::isStartingSpace)
                .toList();
    }

    public List<Hallway> getHallways() {
        return spaces.stream()
                .filter(s -> s instanceof Hallway)
                .map(s -> (Hallway) s)
                .toList();
    }

    public List<IPiece> getAllSuspectPieces() {
        return spaces.stream().flatMap(s -> s.getSuspectPieces().stream()).toList();
    }

    public IPiece findSuspectPieceByName(String name) {
        return getAllSuspectPieces().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
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

        public Builder createBasicTestBoard() {
            Room roomA = new Room(ROOM_A);
            Room roomB = new Room(ROOM_B);
            Room roomC = new Room(ROOM_C);
            Room roomD = new Room(ROOM_D);

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

        public Builder createDisplayTestBoard() {
            Room roomA = new Room(ROOM_A);
            Room roomB = new Room(ROOM_B);
            Room roomC = new Room(ROOM_C);
            Room roomD = new Room(ROOM_D);
            Room roomE = new Room(ROOM_E);
            Room roomF = new Room(ROOM_F);

            Map<String, Hallway> halls = new HashMap<>();
            for (int i = 1; i <= 6; i++) {
                String name = String.valueOf(i);
                halls.put(name, new Hallway(name));
            }

            Map<String, Hallway> starts = new HashMap<>();
            for (int i = 1; i <= DEFAULT_NUM_STARTING_HALLS; i++) {
                String name = STARTING_ROOM + i;
                starts.put(name, new Hallway(name, true));
            }

            halls.values().forEach(this::addSpace);
            starts.values().forEach(this::addSpace);

            addSpace(roomA, roomB, roomC, roomD,  roomE, roomF);

            roomA.connect(EAST, halls.get("1"));
            halls.get("1").connect(EAST, roomB);

            roomB.connect(EAST, halls.get("2"));
            halls.get("2").connect(EAST, roomC);

            roomC.connect(SOUTH, halls.get("3"));
            halls.get("3").connect(SOUTH, roomD);

            roomD.connect(WEST, halls.get("4"));
            halls.get("4").connect(WEST, roomE);

            roomE.connect(WEST, halls.get("5"));
            halls.get("5").connect(WEST, roomF);

            roomF.connect(NORTH, halls.get("6"));
            halls.get("6").connect(NORTH, roomA);

            halls.get("1").connect(NORTH, starts.get("starting 1"));
            halls.get("2").connect(NORTH, starts.get("starting 2"));

            halls.get("3").connect(EAST, starts.get("starting 3"));

            halls.get("4").connect(SOUTH, starts.get("starting 4"));
            halls.get("5").connect(SOUTH, starts.get("starting 5"));

            halls.get("6").connect(WEST, starts.get("starting 6"));


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
            for (int i = 1; i <= DEFAULT_NUM_HALLS; i++) {
                String name = String.valueOf(i);
                halls.put(name, new Hallway(name));
            }

            Map<String, Hallway> starts = new HashMap<>();
            for (int i = 1; i <= DEFAULT_NUM_STARTING_HALLS; i++) {
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

        public Builder placePieces(PieceFactory pieceFactory) {
            List<Room> rooms = spaces.stream()
                    .filter(s -> s instanceof Room)
                    .map(s -> (Room) s)
                    .toList();

            List<Hallway> startingSpaces = spaces.stream()
                    .filter(s -> s instanceof Hallway)
                    .map(s -> (Hallway) s)
                    .filter(Space::isStartingSpace)
                    .toList();

            List<Hallway> hallways = spaces.stream()
                    .filter(s -> s instanceof Hallway)
                    .map(s -> (Hallway) s)
                    .filter(h -> !h.isStartingSpace())
                    .toList();

            String[] suspectNames = pieceFactory.getSuspectNames();
            if (suspectNames.length > startingSpaces.size()) {
                throw new IllegalStateException("Not enough starting hallways.");
            }

            for (int i = 0; i < suspectNames.length; i++) {
                startingSpaces.get(i).addPiece(pieceFactory.createSuspectPiece(suspectNames[i]));
            }

            String[] weaponNames = pieceFactory.getWeaponNames();
            if (weaponNames.length > rooms.size()) {
                throw new IllegalStateException("Not enough rooms for weapons.");
            }

            List<Room> shuffledRooms = new ArrayList<>(rooms);
            Collections.shuffle(shuffledRooms, random);
            for (int i = 0; i < weaponNames.length; i++) {
                shuffledRooms.get(i).addPiece(pieceFactory.createWeaponPiece(weaponNames[i]));
            }

            List<Hallway> shuffledHalls = new ArrayList<>(hallways);
            Collections.shuffle(shuffledHalls, random);
            List<Piece> artifacts = List.of(
                    pieceFactory.createSummonArtifact(),
                    pieceFactory.createTransportArtifact(),
                    pieceFactory.createConcealmentArtifact()
            );

            if (artifacts.size() > shuffledHalls.size()) {
                throw new IllegalStateException("Not enough hallways for artifacts.");
            }

            for (int i = 0; i < artifacts.size(); i++) {
                shuffledHalls.get(i).addPiece(artifacts.get(i));
            }

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

    public Map<Space, int[]> getGridPositions(Space start) {
        Map<Space, int[]> positions = new LinkedHashMap<>();
        Queue<Space> queue = new LinkedList<>();

        positions.put(start, new int[]{0, 0});
        queue.add(start);

        while (!queue.isEmpty()) {
            Space current = queue.poll();
            int[] pos = positions.get(current);

            for (Map.Entry<Direction, Space> entry : current.getNeighbors().entrySet()) {
                Direction dir = entry.getKey();
                Space neighbor = entry.getValue();

                if (dir == Direction.SECRET || positions.containsKey(neighbor)) continue;

                int[] nextPos = switch (dir) {
                    case NORTH -> new int[]{pos[0] - 1, pos[1]};
                    case SOUTH -> new int[]{pos[0] + 1, pos[1]};
                    case EAST  -> new int[]{pos[0], pos[1] + 1};
                    case WEST  -> new int[]{pos[0], pos[1] - 1};
                    default    -> null;
                };

                positions.put(neighbor, nextPos);
                queue.add(neighbor);
            }
        }

        return positions;
    }
}
