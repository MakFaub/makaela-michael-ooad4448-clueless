package clueless.strategy;

import clueless.Player;
import clueless.board.*;
import clueless.cards.CardFactory;
import clueless.cards.Envelope;
import clueless.commands.CommandType;
import clueless.commands.ICommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStrategyTest {

    static class testPlayer extends Player {
        testPlayer() { super("Miss Scarlet"); }

        @Override public boolean hasTransportArtifact() { return true; }
    }

    static class testCommand implements ICommand {
        private final CommandType type;

        testCommand(CommandType type) { this.type = type; }

        @Override public CommandType getType()  { return type; }
        @Override public boolean execute()      { return false; }
        @Override public String optionString()  { return type.name(); }
    }

    private Board board;
    private Space hallway;
    private Space room;

    @BeforeEach
    void setUp() {
        board = new Board.Builder().createBasicBoard().build();
        hallway = board.getSpace("hall AB");
        room = board.getSpace("Room A");
    }

    private PlayerStrategy strategyWithInput(String... lines) {
        String input = String.join("\n", lines) + "\n";
        CardFactory cardFactory = new CardFactory();
        Envelope envelope = new Envelope(
                cardFactory.createRoomCard(),
                cardFactory.createSuspectCard(),
                cardFactory.createWeaponCard()
        );
        return new PlayerStrategy(new Scanner(input), List.of(), board, envelope);
    }

    @Test
    void testGetCommandTypesIsSorted() {
        PlayerStrategy strategy = strategyWithInput();

        ICommand accuse  = new testCommand(CommandType.ACCUSE);
        ICommand suggest = new testCommand(CommandType.SUGGEST);

        List<CommandType> types = strategy.getCommandTypes(List.of(accuse, suggest));

        assertEquals(2, types.size());
        assertTrue(types.contains(CommandType.ACCUSE));
        assertTrue(types.contains(CommandType.SUGGEST));
        assertEquals(types, types.stream().sorted().toList());
    }

    @Test
    void testFilterCommandsByType() {
        PlayerStrategy strategy = strategyWithInput();

        ICommand look    = new testCommand(CommandType.LOOK);
        ICommand suggest = new testCommand(CommandType.TAKE);

        List<ICommand> filtered = strategy.filterCommandsByType(
                List.of(look, suggest), CommandType.LOOK);

        assertEquals(1, filtered.size());
        assertEquals(CommandType.LOOK, filtered.getFirst().getType());
    }

    @Test
    void testAccuseIsAlwaysAnOption() {
        // hallway: MOVE=1, ACCUSE=2, TRANSPORT=3
        PlayerStrategy hallwayStrategy = strategyWithInput("2");
        ICommand hallwayResult = hallwayStrategy.selectAction(new testPlayer(), hallway);
        assertEquals(CommandType.ACCUSE, hallwayResult.getType());

        // room: MOVE=1, SUGGEST=2, ACCUSE=3, TRANSPORT=4
        PlayerStrategy roomStrategy = strategyWithInput("3");
        ICommand roomResult = roomStrategy.selectAction(new testPlayer(), room);
        assertEquals(CommandType.ACCUSE, roomResult.getType());
    }

    @Test
    void testSuggestOnlyAvailableInRoom() {
        // hallway: SUGGEST not available, pick ACCUSE=2
        PlayerStrategy hallwayStrategy = strategyWithInput("2");
        ICommand hallwayResult = hallwayStrategy.selectAction(new testPlayer(), hallway);
        assertNotEquals(CommandType.SUGGEST, hallwayResult.getType());

        // room: MOVE=1, SUGGEST=2, ACCUSE=3, TRANSPORT=4
        PlayerStrategy roomStrategy = strategyWithInput("2");
        ICommand roomResult = roomStrategy.selectAction(new testPlayer(), room);
        assertEquals(CommandType.SUGGEST, roomResult.getType());
    }

    @Test
    void testTransportOptionIsAvailableIfPlayerHasTransportArtifact() {
        // TRANSPORT=3, then pick first room=1
        PlayerStrategy strategy = strategyWithInput("3", "1");
        ICommand result = strategy.selectAction(new testPlayer(), hallway);
        assertEquals(CommandType.TRANSPORT, result.getType());
    }

    @Test
    void testMoveOptionsAvailableWhenSpaceHasNeighbors() {
        // MOVE=1, then pick first neighbor=1
        PlayerStrategy strategy = strategyWithInput("1", "1");
        ICommand result = strategy.selectAction(new testPlayer(), hallway);
        assertEquals(CommandType.MOVE, result.getType());
    }

    @Test
    void testInvalidInput() {
        // "a", "no" are invalid, then ACCUSE=2 (single result, no second input needed)
        PlayerStrategy strategy = strategyWithInput("a", "no", "2");
        ICommand result = strategy.selectAction(new testPlayer(), hallway);
        assertNotNull(result);
    }
}