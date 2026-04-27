package clueless.board;

import clueless.Player;
import clueless.pieces.IPiece;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class BoardDisplay {

    private static final int    CELL_WIDTH      = 14;
    private static final String CONNECTOR       = " - ";
    private static final String NEWLINE         = "\n";

    private Map<IPiece, Integer> playerNumbers;

    protected BoardDisplay(List<Player> players) {
        this.playerNumbers = buildPlayerNumbers(players);
    }

    public void updatePlayers(List<Player> players) {
        this.playerNumbers = buildPlayerNumbers(players);
    }

    protected abstract Space[][] buildGrid();

    public String render() {
        Space[][] grid = buildGrid();
        boolean[][] connectors = buildVerticalConnectors(grid);
        StringBuilder display = new StringBuilder();

        for (int row = 0; row < grid.length; row++) {
            appendSpaceRow(display, grid[row]);
            appendVerticalRow(display, connectors[row]);
        }

        return display.toString();
    }

    private boolean[][] buildVerticalConnectors(Space[][] grid) {
        boolean[][] connectors = new boolean[grid.length][grid[0].length];

        for (int row = 0; row < grid.length - 1; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                Space current = grid[row][col];
                Space below   = grid[row + 1][col];
                connectors[row][col] = current != null && below != null
                        && (current.getNeighbor(Direction.SOUTH) == below
                        ||  current.getNeighbor(Direction.NORTH) == below);
            }
        }

        return connectors;
    }

    private void appendSpaceRow(StringBuilder display, Space[] row) {
        StringBuilder nameLine      = new StringBuilder();
        StringBuilder indicatorLine = new StringBuilder();

        for (int col = 0; col < row.length; col++) {
            Space space = row[col];
            Space prev  = col > 0 ? row[col - 1] : null;
            boolean connected = prev != null && space != null;

            nameLine.append(connected ? CONNECTOR : "   ");
            indicatorLine.append("   ");

            if (space == null) {
                nameLine.append(" ".repeat(CELL_WIDTH + 2));
                indicatorLine.append(" ".repeat(CELL_WIDTH + 2));
            } else {
                BoardCell cell = new BoardCell(space, playerNumbers);
                nameLine.append(formatCell(cell.getName()));
                indicatorLine.append(formatCell(cell.getIndicatorString()));
            }
        }

        display.append(nameLine).append(NEWLINE);
        display.append(indicatorLine).append(NEWLINE);
    }

    private void appendVerticalRow(StringBuilder display, boolean[] connectors) {
        int cellTotal = CELL_WIDTH + 2;
        int leftPad   = cellTotal / 2;
        int rightPad  = cellTotal - leftPad - 1;

        for (boolean connector : connectors) {
            display.append("   ");
            if (connector) {
                display.append(" ".repeat(leftPad)).append("|").append(" ".repeat(rightPad));
            } else {
                display.append(" ".repeat(cellTotal));
            }
        }
        display.append(NEWLINE);
    }

    private String formatCell(String content) {
        if (content.length() > CELL_WIDTH) {
            content = content.substring(0, CELL_WIDTH - 1) + " ";
        }
        int totalPadding = CELL_WIDTH - content.length();
        int leftPad = totalPadding / 2;
        int rightPad = totalPadding - leftPad;
        return "[" + " ".repeat(leftPad) + content + " ".repeat(rightPad) + "]";
    }

    private String blankCell() {
        return "[" + " ".repeat(CELL_WIDTH) + "]";
    }

    private Map<IPiece, Integer> buildPlayerNumbers(List<Player> players) {
        return IntStream.range(0, players.size())
                .filter(i -> players.get(i).getPlayerPiece() != null)
                .boxed()
                .collect(Collectors.toMap(
                        i -> players.get(i).getPlayerPiece(),
                        i -> i + 1
                ));
    }

    private Space[][] toGrid(Map<Space, int[]> positions) {
        int minRow = positions.values().stream().mapToInt(p -> p[0]).min().orElse(0);
        int minCol = positions.values().stream().mapToInt(p -> p[1]).min().orElse(0);
        int maxRow = positions.values().stream().mapToInt(p -> p[0]).max().orElse(0);
        int maxCol = positions.values().stream().mapToInt(p -> p[1]).max().orElse(0);

        Space[][] grid = new Space[maxRow - minRow + 1][maxCol - minCol + 1];
        for (Map.Entry<Space, int[]> entry : positions.entrySet()) {
            grid[entry.getValue()[0] - minRow][entry.getValue()[1] - minCol] = entry.getKey();
        }

        return grid;
    }

    protected Space[][] buildGridFromConnections(Space start, Board board) {
        return toGrid(board.getGridPositions(start));
    }
}