package clueless.commands;

import clueless.Player;
import clueless.board.Room;
import clueless.board.Space;
import clueless.pieces.IPiece;
import clueless.pieces.PieceType;

public class SummonCommand extends Command {
    private final IPiece weapon;
    private final Room weaponRoom;

    public SummonCommand(Player myself, Space space, IPiece weapon, Room weaponRoom){
        super(CommandType.SUMMON, myself, space);
        this.weapon = weapon;
        this.weaponRoom = weaponRoom;
    }

    @Override
    public boolean execute() {
        if (!weaponRoom.contains(weapon)) {
            System.out.println(weaponRoom.getName() + " does not contain " + weapon.getName());
            return false;
        }

        player.takePiece(weapon);
        weaponRoom.removePiece(weapon);

        if (player.hasWeaponPiece()) {
            IPiece oldWeapon = player.getPieceOfType(PieceType.Weapon);
            player.removePiece(oldWeapon);
            weaponRoom.addPiece(weapon);
        }

        return true;
    }

    @Override
    public String optionString() { return weapon.getName(); }
}