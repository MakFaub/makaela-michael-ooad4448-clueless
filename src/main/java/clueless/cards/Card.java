package clueless.cards;

public abstract class Card {
    private final String name;

    Card(String name){ this.name = name; }

    public String getname(){ return name; }

    public boolean isRoomCard(){ return false; }

    public boolean isSuspectCard(){ return false; }

    public boolean isWeaponCard(){ return false; }
}
