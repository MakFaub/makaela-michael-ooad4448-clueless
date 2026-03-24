package clueless.artifacts;

public abstract class Artifact {
    private final String name;

    Artifact(String name){ this.name = name; }

    public String getName(){
        return name;
    }

    public void collectArtifact(){
        // remove Artifact from room, add to player inventory
    }

    public void useArtifact(){
        // complete Concealment/Summon/Transport and remove from player inventory
    }
}
