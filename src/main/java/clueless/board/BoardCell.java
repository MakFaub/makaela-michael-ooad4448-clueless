package clueless.board;

import clueless.Player;
import clueless.pieces.IPiece;
import clueless.pieces.PieceType;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class BoardCell {
    private static final String WEAPON_INDICATOR   = "*W";
    private static final String ARTIFACT_INDICATOR = "*";
    private static final String PLAYER_INDICATOR   = "P";
    private static final String EMPTY_INDICATOR    = "";

    private final String name;
    private final List<String> indicators;

    public BoardCell(Space space, Map<IPiece, Integer> playerNumbers) {
        this.name = space.getName();
        this.indicators = buildCellIndicators(space, playerNumbers);

    }

    private List<String> buildCellIndicators(Space space, Map<IPiece, Integer> playerNumbers) {
        List<String> itemIndicators = space.getPieces().stream()
                .filter(piece -> piece.getType() == PieceType.Weapon || piece.getType().isArtifact())
                .map(piece -> toIndicator(piece, playerNumbers))
                .toList();

        List<String> playerIndicators = space.getPieces().stream()
                .filter(playerNumbers::containsKey)
                .sorted(Comparator.comparingInt(playerNumbers::get))
                .map(piece -> toIndicator(piece, playerNumbers))
                .toList();

        return Stream.concat(itemIndicators.stream(), playerIndicators.stream()).toList();
    }

    private String toIndicator(IPiece piece, Map<IPiece, Integer> playerNumbers) {
        if(piece.isType(PieceType.Weapon)) return WEAPON_INDICATOR;

        if(piece.getType().isArtifact()) return ARTIFACT_INDICATOR + piece.getType().name().charAt(0);

        Integer playerNumber = playerNumbers.get(piece);
        if(playerNumber != null) return PLAYER_INDICATOR + playerNumber;

        return EMPTY_INDICATOR;
    }

    public String getName() {
        return name;
    }
    public String getIndicatorString() {
        return String.join(" ", indicators);
    }

    public boolean hasIndicators() {
        return !indicators.isEmpty();
    }
}
