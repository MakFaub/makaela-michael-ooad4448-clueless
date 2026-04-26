package clueless.commands;

import clueless.Player;
import clueless.board.Space;
import clueless.cards.Card;
import clueless.pieces.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class LookCommand extends Command {
    private static final Logger logger = Logger.getLogger(LookCommand.class.getName());

    private final Player otherPlayer;

    public LookCommand(Player myself, Player otherPlayer, Space space){
        super(CommandType.LOOK, myself, space);
        this.otherPlayer = otherPlayer;
    }

    @Override
    public boolean execute() {
        if (!player.hasConcealmentArtifact()){
            logger.warning(player.getName() + "does not have a concealment artifact and cannot look at another player's card.");
            return false;
        }

        List<Card> hand = new ArrayList<>(otherPlayer.getHand());
        if (hand.isEmpty()) {
            logger.warning(otherPlayer.getName() + " has no cards.");
            return false;
        }

        Card revealed = hand.get(new Random().nextInt(hand.size()));
        player.discoverCard(revealed);
        logger.info(player.getName() + ", you peeked at " + otherPlayer.getName() + "'s hand and saw: " + revealed.getName());

        player.removePiece(player.getPieceOfType(PieceType.Conceal));

        return true;
    }

    @Override
    public String optionString() {
        return otherPlayer.getName();
    }
}
