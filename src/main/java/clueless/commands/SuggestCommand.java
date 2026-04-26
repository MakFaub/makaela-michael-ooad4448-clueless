package clueless.commands;

import clueless.Player;
import clueless.board.Board;
import clueless.board.Room;
import clueless.board.Space;
import clueless.cards.Card;
import clueless.pieces.IPiece;
import clueless.pieces.PieceType;
import clueless.pieces.SuspectPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class SuggestCommand extends Command {
    private static final Logger logger = Logger.getLogger(TakeCommand.class.getName());

    private final Board board;
    private final List<Player> players;
    private final IInputHandler inputHandler;

    public SuggestCommand(Player myself, Space space, Board board, List<Player> players, IInputHandler inputHandler) {
        super(CommandType.SUGGEST, myself, space);
        this.board = board;
        this.players = players;
        this.inputHandler = inputHandler;
    }

    private IPiece chooseWeapon() {
        List<IPiece> weaponOptions = new ArrayList<>(space.getWeaponPieces());
        IPiece heldWeapon = player.getPieceOfType(PieceType.Weapon);
        if (heldWeapon != null && !weaponOptions.contains(heldWeapon)) {
            weaponOptions.add(heldWeapon);
        }

        if (weaponOptions.isEmpty()) {
            logger.warning("There are no weapons available to make a suggestion.");
            return null;
        }

        System.out.println("Choose a weapon for your suggestion:");
        for (int i = 0; i < weaponOptions.size(); i++) {
            System.out.println((i + 1) + ". " + weaponOptions.get(i).getName());
        }

        return inputHandler.choose(weaponOptions);
    }

    private IPiece chooseSuspect() {
        List<IPiece> suspectOptions = board.getSpaces().stream().flatMap(s -> s.getSuspectPieces().stream()).toList();

        System.out.println("Choose a suspect for your suggestion:");
        for (int i = 0; i < suspectOptions.size(); i++) {
            System.out.println((i + 1) + ". " + suspectOptions.get(i).getName());
        }
        return inputHandler.choose(suspectOptions);
    }

    private Card checkSuggestionAgainstPlayerHands(IPiece suspect, IPiece weapon) {
        int currentIndex = players.indexOf(player);

        for (int i = 1; i < players.size(); i++) {
            Player otherPlayer = players.get((currentIndex + i) % players.size());

            List<Card> matchingCards = otherPlayer.getHand().stream().filter(card -> card.matches(suspect) || card.matches(weapon) || card.matches(space))
                    .toList();

            if (!matchingCards.isEmpty()) {
                Card revealed = matchingCards.get(new Random().nextInt(matchingCards.size()));
                logger.info(otherPlayer.getName() + " revealed a card.");
                return revealed;
            }
        }

        return null;
    }

    @Override
    public boolean execute() {
        if (!space.isRoom()) {
            logger.warning(player.getName() + " is not in a room and cannot make a suggestion.");
            return false;
        }

        Room currentRoom = (Room) space;

        IPiece weapon = chooseWeapon();
        if (weapon == null) { return false; }

        IPiece suspect = chooseSuspect();

        Space suspectCurrentSpace = board.getRoomBasedOnPiece(suspect);
        suspectCurrentSpace.removePiece(suspect);
        currentRoom.addPiece(suspect);

        logger.info(player.getName() + " suggests: " + suspect.getName()
                + " in the " + currentRoom.getName()
                + " with the " + weapon.getName() + ".");

        Card learnedCard = checkSuggestionAgainstPlayerHands(suspect, weapon);

        if (learnedCard == null) {
            logger.info("Other players did not have any cards matching suggestion.");
        } else {
            player.discoverCard(learnedCard);
        }

        return true;
    }
}