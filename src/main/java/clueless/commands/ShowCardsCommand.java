package clueless.commands;

import clueless.Player;
import clueless.board.Space;
import clueless.cards.Card;

public class ShowCardsCommand extends Command {
    public ShowCardsCommand(Player player, Space space) {
        super(CommandType.SHOW_CARDS, player, space);
    }

    @Override
    public boolean execute(){
        for (Card card : player.getDiscoveredCards()) {
            System.out.println(card.getName());
        }
        return true;
    }
}
