package clueless;

import clueless.cards.Card;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final List<Card> hand = new ArrayList();
    private List<Card> discoveredCards = new ArrayList();

    Player(String name){ this.name = name; }

    public String getName(){ return name; }
}
