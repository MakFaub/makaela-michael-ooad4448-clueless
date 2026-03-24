package clueless.artifacts;

public class ConcealmentArtifact extends Artifact {
    ConcealmentArtifact(String name){ super(name); }

    @Override
    public void useArtifact(){
        // Concealment: let player view one random card from another player's hand
        // Remove artifact
    }
}
