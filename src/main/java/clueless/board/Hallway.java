package clueless.board;

import clueless.Player;

public class Hallway extends Space {
    public Hallway(String name) {
        super(name);
    }

    @Override
    public boolean isOccupied() {
        return !getPlayers().isEmpty();
    }

    @Override
    public boolean enter(Player player) {
        if(isOccupied()){ return false; }
        else{
            super.enter(player);
            return true;
        }
    }
}