package clueless.cards;

public class WeaponCard extends Card{
    WeaponCard(String name){ super(name);}

    @Override
    public boolean isWeaponCard(){ return true; }
}
