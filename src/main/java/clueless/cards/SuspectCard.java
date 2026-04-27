package clueless.cards;

public class SuspectCard extends Card{
    public SuspectCard(String name){ super(name);}

    @Override
    public boolean isSuspectCard(){ return true; }
}
