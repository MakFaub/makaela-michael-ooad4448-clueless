package clueless.commands;

import clueless.Player;
import clueless.board.Space;
import clueless.cards.Card;
import clueless.cards.CardList;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ShowCardsCommand extends Command {
    private final CardList cards;

    public ShowCardsCommand(Player player, Space space, CardList cards) {
        super(CommandType.SHOW_CARDS, player, space);
        this.cards = cards;
    }

    @Override
    public boolean execute(){
        Set<String> handCards = getCardNames(player.getHand());
        Set<String> discoveredCards = getCardNames(player.getDiscoveredCards());

        System.out.println();
        System.out.println("=== " + player.getName() + "'s Notes ===");

        displayCards(handCards, discoveredCards);

        System.out.println();
        System.out.println("[H] in hand  -  [X] discovered");
        System.out.println();
        return true;
    }

    private void displayCards(Set<String> handCards, Set<String> discoveredCards){
        displayGroup("SUSPECTS", cards.suspects(), handCards, discoveredCards);
        displayGroup("WEAPONS", cards.weapons(), handCards, discoveredCards);
        displayGroup("ROOMS", cards.rooms(), handCards, discoveredCards);
    }

    private void displayGroup(String category, List<String> cardNames, Set<String> handCards, Set<String> discoveredCards){
        System.out.println(category);
        for (String card : cardNames) {
            System.out.println(" " + marker(card,handCards,discoveredCards) + " " + card);
        }
    }

    private String marker(String name, Set<String> hand, Set<String> discovered){
        if (hand.contains(name)) return "[H]";
        if (discovered.contains(name)) return "[X]";
        return "[ ]";
    }

    private Set<String> getCardNames(Set<Card> cards){
        return cards.stream().map(Card::getName).collect(Collectors.toSet());
    }
}
