package clueless.commands;

import clueless.EventBus;
import clueless.Player;
import clueless.board.Room;
import clueless.board.Space;
import clueless.observers.EventType;
import clueless.pieces.IPiece;
import clueless.pieces.PieceType;
import clueless.pieces.SummonArtifact;

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

        if (player.hasWeaponPiece()) {
            IPiece oldWeapon = player.getPieceOfType(PieceType.Weapon);
            player.removePiece(oldWeapon);
            weaponRoom.addPiece(oldWeapon);
            logger.info(player.getName() + ", you dropped weapon: " + oldWeapon.getName());
        }

        player.takePiece(weapon);
        weaponRoom.removePiece(weapon);
        logger.info(player.getName() + ", you picked up weapon: " + weapon.getName());

        IPiece summonArtifact = player.getPieceOfType(PieceType.Summon);
        player.removePiece(summonArtifact);
        EventBus.getInstance().postEvent(EventType.ARTIFACT_USED, summonArtifact.getName());

        return true;
    }

    @Override
    public String optionString() { return weapon.getName(); }
}
