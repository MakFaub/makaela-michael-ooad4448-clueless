package clueless.commands;

import clueless.EventBus;
import clueless.Player;
import clueless.board.Board;
import clueless.board.Room;
import clueless.board.Space;
import clueless.cards.Card;
import clueless.cards.Envelope;
import clueless.observers.EventType;
import clueless.pieces.IPiece;
import clueless.pieces.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class AccuseCommand extends Command {
    private static final Logger logger = Logger.getLogger(AccuseCommand.class.getName());

    private final Envelope envelope;
    private final Board board;
    private final List<Player> players;
    private final IInputHandler inputHandler;

    public AccuseCommand(Player myself, Space space, Board board, Envelope envelope, List<Player> players, IInputHandler inputHandler) {
        super(CommandType.ACCUSE, myself, space);
        this.envelope = envelope;
        this.board = board;
        this.players = players;
        this.inputHandler = inputHandler;
    }

    private List<IPiece> getAllWeapons() {
        List<IPiece> weapons = new ArrayList<>(board.getAllAvailableWeaponPieces());
        players.stream()
                .map(player -> player.getPieceOfType(PieceType.Weapon)).filter(Objects::nonNull).forEach(weapons::add);
        return weapons;
    }

    private IPiece chooseWeapon() {
        List<IPiece> weapons = getAllWeapons();

        System.out.println("Choose a weapon for your accusation:");
        for (int i = 0; i < weapons.size(); i++) {
            System.out.println((i + 1) + ". " + weapons.get(i).getName());
        }

        return inputHandler.choose(weapons);
    }

    private IPiece chooseSuspect() {
        List<IPiece> suspects = board.getAllSuspectPieces();

        System.out.println("Choose a suspect for your accusation:");
        for (int i = 0; i < suspects.size(); i++) {
            System.out.println((i + 1) + ". " + suspects.get(i).getName());
        }

        return inputHandler.choose(suspects);
    }

    private Room chooseRoom() {
        List<Room> rooms = board.getRooms();

        System.out.println("Choose a room for your accusation:");
        for (int i = 0; i < rooms.size(); i++) {
            System.out.println((i + 1) + ". " + rooms.get(i).getName());
        }

        return inputHandler.choose(rooms);
    }

    @Override
    public boolean execute() {
        IPiece weapon = chooseWeapon();
        IPiece suspect = chooseSuspect();
        Room room = chooseRoom();

        boolean guess = envelope.checkGuess(
                new clueless.cards.SuspectCard(suspect.getName()),
                new clueless.cards.WeaponCard(weapon.getName()),
                new clueless.cards.RoomCard(room.getName())
        );

        if (guess) {
            logger.info(player.getName() + " made a correct accusation and wins!");
        } else {
            logger.warning(player.getName() + " made a wrong accusation and is eliminated.");
        }

        EventBus.getInstance().postEvent(EventType.ACCUSATION_OCCURRED, player.getName() + " accused " + suspect.getName());

        return guess;
    }
}