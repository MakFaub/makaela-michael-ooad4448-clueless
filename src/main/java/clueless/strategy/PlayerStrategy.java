package clueless.strategy;

import clueless.Player;
import clueless.board.Board;
import clueless.board.Room;
import clueless.board.Space;
import clueless.cards.Envelope;
import clueless.commands.CommandFactory;
import clueless.commands.CommandType;
import clueless.commands.ICommand;
import clueless.pieces.IPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PlayerStrategy {
    static protected CommandFactory commandFactory = new CommandFactory();
    private final List<Player> players;
    private final Board board;
    private final Envelope envelope;

    private final Scanner scanner;

    PlayerStrategy(Scanner scanner, List<Player> players, Board board, Envelope envelope) {
        this.scanner = scanner;
        this.players = players;
        this.board = board;
        this.envelope = envelope;
    }

    public PlayerStrategy(List<Player> players, Board board, Envelope envelope) {
        this.scanner = new Scanner(System.in);
        this.players = players;
        this.board = board;
        this.envelope = envelope;
    }

    public ICommand selectAction(Player player, Space space) {
        if (space.isHallway()) {
            System.out.print(player.getName() + ", you are in the hallway. \n");
        }
        if (space.isRoom()) {
            System.out.print(player.getName() + ", you are in the " + space.getName() + ". \n");
        }
        System.out.print("Choose an action." + "\n\n");

        List<ICommand> options = getAvailableOptions(player, space);

        CommandType commandType = getTopLevelChoice(options, player, space);

        List<ICommand> subCommands = filterCommandsByType(options, commandType);

        if (subCommands.size() == 1) { return subCommands.getFirst(); }

        else return getSecondLevelChoice(subCommands, commandType);
    }

    private List<ICommand> getAvailableOptions(Player player, Space space) {
        List<ICommand> availableOptions = new ArrayList<>();

        // options available regardless of where player is on board
        availableOptions.add(commandFactory.newAccuseCommand(player, space, board, envelope, players, this::getUserInputChoice));

        if (space.hasNeighbors()) {
            for (Space neighbor : space.getNeighbors().values()) {
                availableOptions.add(commandFactory.newMoveCommand(player, space, neighbor));
            }
        }

        if (player.hasTransportArtifact()){
            for (Room room : board.getRooms()){
                availableOptions.add(commandFactory.newTransportCommand(player, space, room));
            }
        }

        if (player.hasConcealmentArtifact()) {
            List<Player> otherPlayers = players.stream().filter(p -> !p.equals(player)).toList();

            for (Player otherPlayer : otherPlayers) {
                availableOptions.add(commandFactory.newLookCommand(player, otherPlayer, space));
            }
        }

        // can only summon weapons not artifacts
        if (player.hasSummonArtifact()){
            List<IPiece> availableWeapons = board.getAllAvailableWeaponPieces();
            Room weaponRoom;
            for (IPiece weapon : availableWeapons) {
                weaponRoom = board.getRoomBasedOnPiece(weapon);
                availableOptions.add(commandFactory.newSummonCommand(player, space, weapon, weaponRoom));
            }
        }

        // options only available if player is in room space
        if (space.isRoom()){
            availableOptions.add(commandFactory.newSuggestCommand(player, space, board, players, this::getUserInputChoice));

            for (IPiece weapons : space.getWeaponPieces()) { availableOptions.add(commandFactory.newTakeCommand(player, space, weapons, this::getUserInputChoice)); }

            for (IPiece artifacts : space.getArtifactPieces()) { availableOptions.add(commandFactory.newTakeCommand(player, space, artifacts, this::getUserInputChoice)); }
        }

        return availableOptions;
    }

    List<CommandType> getCommandTypes(List<ICommand> options) {
        return options.stream().map(ICommand::getType).sorted().distinct().toList();
    }

    List<ICommand> filterCommandsByType(List<ICommand> options, CommandType type) {
        return options.stream().filter(command -> command.getType() == type).collect(Collectors.toList());
    }

    CommandType getTopLevelChoice(List<ICommand> options, Player player, Space space) {
        List<CommandType> topLevelOptions = getCommandTypes(options);

        for (int i = 0; i < topLevelOptions.size(); i++) {System.out.println((i + 1) + ". " + topLevelOptions.get(i));}

        return getUserInputChoice(topLevelOptions);
    }

    private ICommand getSecondLevelChoice(List<ICommand> options, CommandType type) {
        System.out.println("Select " + type.name().toLowerCase() + " target:\n");

        for (int i = 0; i < options.size(); i++) { System.out.println((i + 1) + ". " + options.get(i).optionString()); }

        return getUserInputChoice(options);
    }

    private <T> T getUserInputChoice(List<T> options){
        while (true) {
            System.out.print("Enter your option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                if (choice >= 1 && choice <= options.size()) { return options.get(choice - 1); }

            } catch (NumberFormatException ignored) {}

            System.out.println("Invalid option.");
        }
    }
}
