package clueless.strategy;

import clueless.Player;
import clueless.board.Direction;
import clueless.board.Space;
import clueless.commands.CommandType;
import clueless.commands.ICommand;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStrategyTest {

    static class testPlayer extends Player {
        testPlayer() { super("Miss Scarlet"); }

        @Override public boolean hasTransportArtifact() { return true; }
    }

    static class testHallwaySpace extends Space {
        testHallwaySpace() { super("Hallway"); }

        @Override public boolean isHallway() { return true; }
        @Override public boolean hasNeighbors() { return false; }
        @Override public Map<Direction, Space> getNeighbors() { return Map.of(); }
    }

    static class testRoomSpace extends Space {
        testRoomSpace() { super("Kitchen"); }

        @Override public boolean isRoom() { return true; }
        @Override public boolean hasNeighbors() { return false; }
        @Override public Map<Direction, Space> getNeighbors() { return Map.of(); }
    }

    static class testCommand implements ICommand {
        private final CommandType type;

        testCommand(CommandType type) { this.type = type; }

        @Override public CommandType getType()     { return type; }
        @Override public boolean execute()            { return false; }
        @Override public String optionString()     { return type.name(); }
    }



    private PlayerStrategy strategyWithInput(String... lines) {
        String input = String.join("\n", lines) + "\n";
        return new PlayerStrategy(new Scanner(input));
    }

    @Test
    void testGetCommandTypesIsSorted() {
        PlayerStrategy strategy = strategyWithInput();

        ICommand guess   = new testCommand(CommandType.ACCUSE);
        ICommand suggest = new testCommand(CommandType.SUGGEST);

        List<CommandType> types = strategy.getCommandTypes(List.of(guess, suggest));

        assertEquals(2, types.size());
        assertTrue(types.contains(CommandType.ACCUSE));
        assertTrue(types.contains(CommandType.SUGGEST));
        assertEquals(types, types.stream().sorted().toList());
    }

    @Test
    void testFilterCommandsByType() {
        PlayerStrategy strategy = strategyWithInput();

        ICommand guess   = new testCommand(CommandType.LOOK);
        ICommand suggest = new testCommand(CommandType.TAKE);

        List<ICommand> filtered = strategy.filterCommandsByType(
                List.of(guess, suggest), CommandType.LOOK);

        assertEquals(1, filtered.size());
        assertEquals(CommandType.LOOK, filtered.getFirst().getType());
    }

    @Test
    void testGuessIsAlwaysAnOption() {
        PlayerStrategy strategy = strategyWithInput("1", "2");
        ICommand hallwayResult = strategy.selectAction(new testPlayer(), new testHallwaySpace());
        assertEquals(CommandType.ACCUSE, hallwayResult.getType());

        ICommand roomResult = strategy.selectAction(new testPlayer(), new testRoomSpace());
        assertEquals(CommandType.ACCUSE, roomResult.getType());
    }

    @Test
    void testSuggestOnlyAvailableInRoom() {
        PlayerStrategy strategy = strategyWithInput("1", "1");

        ICommand hallwayResult = strategy.selectAction(new testPlayer(), new testHallwaySpace());
        assertNotEquals(CommandType.SUGGEST, hallwayResult.getType());

        ICommand roomResult = strategy.selectAction(new testPlayer(), new testRoomSpace());
        assertEquals(CommandType.SUGGEST, roomResult.getType());
    }

    @Test
    void testTransportOptionIsAvailableIfPlayerHasTransportArtifact() {
        testPlayer player = new testPlayer();

        PlayerStrategy strategy = strategyWithInput("2"); // Guess=1, Transport=2
        ICommand result = strategy.selectAction(player, new testHallwaySpace());
        assertEquals(CommandType.TRANSPORT, result.getType());
    }

    @Test
    void testInvalidInput() {
        PlayerStrategy strategy = strategyWithInput("a", "no", "1");
        ICommand result = strategy.selectAction(new testPlayer(), new testHallwaySpace());
        assertNotNull(result);
    }
}