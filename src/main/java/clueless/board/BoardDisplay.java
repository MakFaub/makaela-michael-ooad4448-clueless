package clueless.board;

import clueless.Player;
import clueless.pieces.IPiece;

import java.util.*;
import java.util.stream.Collectors;

public class BoardDisplay {
    private BoardDisplay() {}

    public static String render(Board board, List<Player> players) {
        Map<IPiece, String> playerNames = mapPlayerNames(players);

        StringBuilder display = new StringBuilder();
        addRooms(display, board, playerNames);
        display.append("\n");
        addHallways(display, board, playerNames);
        return display.toString();
    }

    private static void addRooms(StringBuilder display, Board board, Map<IPiece, String> playerNames) {
        display.append("ROOMS\n");
        for (Room room : board.getRooms()) {
            display.append(" ")
                    .append(room.getName())
                    .append(" - ")
                    .append(describeSpace(room, playerNames))
                    .append('\n');
        }


    }

        private static void addHallways(StringBuilder display, Board board, Map<IPiece, String> playerNames) {
        display.append("HALLWAYS\n");

        List<Hallway> hallways = board.getHallways()
                .stream()
                .filter(h -> !h.isStartingSpace())
                .sorted(Comparator.comparingInt(h -> Integer.parseInt(h.getName())))
                .toList();

        List<Hallway> starting = board.getStartingHallways()
                .stream()
                .sorted(Comparator.comparingInt(h -> Integer.parseInt(h.getName().replace("starting ", ""))))
                .toList();

        int emptyCount = 0;
        for (Hallway hallway : hallways) {
            if (spaceIsEmpty(hallway)) emptyCount++;
            else {
                display.append("  ").append(hallway.getName())
                        .append(" - ").append(describeSpace(hallway, playerNames))
                        .append('\n');
            }
        }
        for (Hallway hallway : starting) {
            if (spaceIsEmpty(hallway)) emptyCount++;
            else {
                display.append("  ").append(hallway.getName())
                        .append(" - ").append(describeSpace(hallway, playerNames))
                        .append('\n');
            }
        }

        if (emptyCount > 0) display.append("  (").append(emptyCount).append(" empty hallways)\n");

    }



    private static boolean spaceIsEmpty(Hallway hallway) {
        return hallway.getPieces().isEmpty();
    }

    private static String describeSpace(Space space, Map<IPiece, String> playerNames) {
        List<String> parts = space.getPieces().stream()
                .map(piece -> {
                    if (piece.getType().isArtifact()) {
                        return piece.getType().name() + " Artifact";
                    }
                    String playerName = playerNames.get(piece);
                    if (playerName != null) {
                        return piece.getName() + " (" + playerName + ")";
                    } else {
                        return piece.getName();
                    }
                })
                .collect(Collectors.toList());

        return parts.isEmpty() ? "empty" : String.join(", ", parts);
    }

    private static Map<IPiece, String> mapPlayerNames(List<Player> players) {
        return players.stream()
                .filter(player -> player.getPlayerPiece() != null)
                .collect(Collectors.toMap(Player::getPlayerPiece, Player::getName));
    }


}
