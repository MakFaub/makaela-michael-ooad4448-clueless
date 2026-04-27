package clueless.board;

import clueless.pieces.IPiece;
import clueless.pieces.PieceType;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Space {
    private final String name;
    private final Map<Direction, Space> neighbors = new EnumMap<Direction, Space>(Direction.class);
    private boolean startingSpace;

    private final Set<IPiece> pieces = new HashSet<>();

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

    public void connect(Direction direction, Space neighbor){
        if(neighbor != null){
            neighbors.put(direction, neighbor);
            neighbor.neighbors.put(direction.opposite(), this);
        }
    }

    public Space getNeighbor(Direction direction){
        return neighbors.get(direction);
    }

    public Map<Direction, Space> getNeighbors(){
        return Map.copyOf(neighbors);
    }

    public Set<IPiece> getPieces(){
        return Set.copyOf(pieces);
    }

    public Set<IPiece> getSuspectPieces() {
        return pieces.stream().filter(piece -> piece.getType() == PieceType.Suspect).collect(Collectors.toSet());
    }

    public boolean isOccupied() {
        return !getSuspectPieces().isEmpty();
    }

    public void addPiece(IPiece piece) {
        pieces.add(piece);
    }

    public void removePiece(IPiece piece) {
        pieces.remove(piece);
    }

    public String getName(){
        return name;
    }

    public boolean isRoom() { return false; }

    public boolean isHallway() { return false; }

    public Set<IPiece> getWeaponPieces() {
        return pieces.stream().filter(piece -> piece.getType() == PieceType.Weapon).collect(Collectors.toSet());
    }

    public Set<IPiece> getArtifactPieces() {
        return pieces.stream().filter(piece -> piece.getType().isArtifact()).collect(Collectors.toSet());
    }

    public boolean hasNeighbors() {
        return !neighbors.isEmpty();
    }

    public boolean contains(IPiece piece) { return pieces.contains(piece); }

    public boolean hasNeighbor(Space neighbor) {
        return neighbors.containsValue(neighbor);
    }
}
