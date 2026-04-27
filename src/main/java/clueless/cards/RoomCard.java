package clueless.cards;

public class RoomCard extends Card {
    public RoomCard(String name){ super(name);}

    @Override
    public boolean isRoomCard(){ return true; }
}
