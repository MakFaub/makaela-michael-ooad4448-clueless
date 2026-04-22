package clueless;

import clueless.cards.Card;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final List<Card> hand = new ArrayList<>();
    private final List<Card> discoveredCards = new ArrayList<>();

    public Player(String name){ this.name = name; }

    public String getName(){ return name; }

    public void discoverCard(Card card) { discoveredCards.add(card); }

    public List<Card> getDiscoveredCards() { return discoveredCards; }

    public List<Card> getHand() { return hand; }

    public void addCardsToHand(List<Card> cards) {
        hand.addAll(cards);
        discoveredCards.addAll(cards);
    }
}
