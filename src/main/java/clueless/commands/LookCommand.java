package clueless.commands;

import clueless.Player;
import clueless.board.Space;
import clueless.cards.Card;
import clueless.pieces.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LookCommand extends Command {
    private final List<Player> players;
    private final IInputHandler inputHandler;


    public LookCommand(Player myself, List<Player> players, Space space, IInputHandler inputHandler){
        super(CommandType.LOOK, myself, space);
        this.players = players;
        this.inputHandler = inputHandler;
    }

    private Player getPlayerToPeekAt(){
        List<Player> otherPlayers = players.stream().filter(p -> !p.equals(player)).toList();

        if (otherPlayers.isEmpty()) {
            return null;
        }

        Player target;
        if (otherPlayers.size() == 1) {
            target = otherPlayers.getFirst();
        } else {
            System.out.println("Which player's hand would you like to peek at?");
            for (int i = 0; i < otherPlayers.size(); i++) {
                System.out.println((i + 1) + ". " + otherPlayers.get(i).getName());
            }
            target = inputHandler.choose(otherPlayers);
        }
        return target;
    }

    // TODO: replace prints with logger
    @Override
    public boolean execute() {
        if (!player.hasConcealmentArtifact()){
            System.out.println(player.getName() + "does not have a concealment artifact and cannot look at another player's card.");
            return false;
        }

        Player target = getPlayerToPeekAt();

        if (target == null){
            System.out.println("There are no other players to peek at.");
            return false;
        }

        List<Card> hand = new ArrayList<>(target.getHand());
        if (hand.isEmpty()) {
            System.out.println(target.getName() + " has no cards.");
            return false;
        }

        Card revealed = hand.get(new Random().nextInt(hand.size()));
        player.discoverCard(revealed);
        System.out.println("You peeked at " + target.getName() + "'s hand and saw: " + revealed.getName());

        player.removePiece(player.getPieceOfType(PieceType.Conceal));

        return true;
    }
}
