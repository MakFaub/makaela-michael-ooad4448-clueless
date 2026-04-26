package clueless.commands;

import clueless.Player;
import clueless.board.Space;
import clueless.cards.Card;
import clueless.pieces.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LookCommand extends Command {
    private final Player otherPlayer;


    public LookCommand(Player myself, Player otherPlayer, Space space){
        super(CommandType.LOOK, myself, space);
        this.otherPlayer = otherPlayer;
    }

    // TODO: replace prints with logger
    @Override
    public boolean execute() {
        if (!player.hasConcealmentArtifact()){
            System.out.println(player.getName() + "does not have a concealment artifact and cannot look at another player's card.");
            return false;
        }

        List<Card> hand = new ArrayList<>(otherPlayer.getHand());
        if (hand.isEmpty()) {
            System.out.println(otherPlayer.getName() + " has no cards.");
            return false;
        }

        Card revealed = hand.get(new Random().nextInt(hand.size()));
        player.discoverCard(revealed);
        System.out.println("You peeked at " + otherPlayer.getName() + "'s hand and saw: " + revealed.getName());

        player.removePiece(player.getPieceOfType(PieceType.Conceal));

        return true;
    }

    @Override
    public String optionString() {
        return otherPlayer.getName();
    }
}