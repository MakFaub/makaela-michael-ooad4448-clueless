package clueless.board;

import clueless.pieces.IPiece;
import clueless.pieces.Piece;
import clueless.pieces.PieceType;
import clueless.pieces.SuspectPiece;

import java.util.*;

public abstract class Space {
    private final String name;
    private final Map<Direction, Space> neighbors = new EnumMap<Direction, Space>(Direction.class);
    private boolean startingSpace;

    private final List<Piece> pieces = new ArrayList<Piece>();

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

    public List<Piece> getPieces(){
        return List.copyOf(pieces);
    }

    public List<Piece> getSuspectPieces() {
        return pieces.stream().filter(piece -> piece.getType() == PieceType.Suspect).toList();
    }

    public boolean isAvailable(){
        return !isOccupied();
    }

    public boolean isOccupied(){
        return pieces.stream().anyMatch(piece -> piece.isType(PieceType.Suspect));
    }

    public boolean enter(Piece piece){
        return addPiece(piece);
    }

    public void leave(Piece piece){
        removePiece(piece);
    }

    public boolean addPiece(Piece piece) {
        pieces.add(piece);
        return true;
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public String getName(){
        return name;
    }

    public boolean isRoom() { return false; }

    public boolean isHallway() { return false; }

    public List<Piece> getWeaponPieces() {
        return pieces.stream().filter(piece -> piece.getType() == PieceType.Weapon).toList();
    }

    public List<Piece> getArtifactPieces() {
        return pieces.stream().filter(piece -> piece.getType().isArtifact()).toList();
    }

    public boolean hasNeighbors() {
        return !neighbors.isEmpty();
    }
}
