package clueless.commands;

import clueless.Player;
import clueless.board.Room;
import clueless.board.Space;
import clueless.pieces.IPiece;
import clueless.pieces.PieceType;

import java.util.logging.Logger;

public class SummonCommand extends Command {
    private static final Logger logger = Logger.getLogger(SummonCommand.class.getName());

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
            logger.info(weaponRoom.getName() + " does not contain " + weapon.getName());
            return false;
        }

        player.takePiece(weapon);
        weaponRoom.removePiece(weapon);
        logger.info(player.getName() + ", you picked up weapon: " + weapon.getName());

        if (player.hasWeaponPiece()) {
            IPiece oldWeapon = player.getPieceOfType(PieceType.Weapon);
            player.removePiece(oldWeapon);
            weaponRoom.addPiece(weapon);
            logger.info(player.getName() + ", you dropped weapon: " + oldWeapon.getName());
        }

        player.removePiece(player.getPieceOfType(PieceType.Summon));

        return true;
    }

    @Override
    public String optionString() { return weapon.getName(); }
}
