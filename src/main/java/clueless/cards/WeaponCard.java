package clueless.cards;

public class WeaponCard extends Card{
    public WeaponCard(String name){ super(name);}

    @Override
    public boolean isWeaponCard(){ return true; }
}
