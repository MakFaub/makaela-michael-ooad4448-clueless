package clueless.cards;

public abstract class Card {
    private final String name;

    Card(String name){ this.name = name; }

    public String getname(){ return name; }
}
