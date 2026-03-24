package clueless.artifacts;

public abstract class Artifact {
    private final String name;

    Artifact(String name){ this.name = name; }

    public String getName() {
        return name;
    }
}
