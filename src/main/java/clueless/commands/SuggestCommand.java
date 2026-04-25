package clueless.commands;

import clueless.Player;
import clueless.board.Board;
import clueless.board.Room;
import clueless.board.Space;
import clueless.pieces.IPiece;
import clueless.pieces.PieceType;
import clueless.pieces.SuspectPiece;

import java.util.ArrayList;
import java.util.List;

public class SuggestCommand extends Command {
    private final Board board;
    private final IInputHandler inputHandler;

    public SuggestCommand(Player myself, Space space, Board board, IInputHandler inputHandler) {
        super(CommandType.SUGGEST, myself, space);
        this.board = board;
        this.inputHandler = inputHandler;
    }

    @Override
    public boolean execute() {
        if (!space.isRoom()) {
            System.out.println(player.getName() + " is not in a room and cannot make a suggestion.");
            return false;
        }

        Room currentRoom = (Room) space;

        List<IPiece> weaponOptions = new ArrayList<>(currentRoom.getWeaponPieces());
        IPiece heldWeapon = player.getPieceOfType(PieceType.Weapon);
        if (heldWeapon != null && !weaponOptions.contains(heldWeapon)) {
            weaponOptions.add(heldWeapon);
        }

        if (weaponOptions.isEmpty()) {
            System.out.println("There are no weapons available to make a suggestion.");
            return false;
        }

        List<IPiece> suspectOptions = board.getSpaces().stream().flatMap(s -> s.getSuspectPieces().stream()).toList();

        System.out.println("Choose a weapon for your suggestion:");
        for (int i = 0; i < weaponOptions.size(); i++) {
            System.out.println((i + 1) + ". " + weaponOptions.get(i).getName());
        }
        IPiece chosenWeapon = inputHandler.choose(weaponOptions);

        // choose suspect
        System.out.println("Choose a suspect for your suggestion:");
        for (int i = 0; i < suspectOptions.size(); i++) {
            System.out.println((i + 1) + ". " + suspectOptions.get(i).getName());
        }
        IPiece chosenSuspect = inputHandler.choose(suspectOptions);

        Space suspectCurrentSpace = board.getRoomBasedOnPiece(chosenSuspect);
        suspectCurrentSpace.removePiece(chosenSuspect);
        currentRoom.addPiece(chosenSuspect);

        System.out.println(player.getName() + " suggests: " + chosenSuspect.getName()
                + " in the " + currentRoom.getName()
                + " with the " + chosenWeapon.getName() + ".");

        return true;
    }
}