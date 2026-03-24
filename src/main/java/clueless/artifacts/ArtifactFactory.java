package clueless.artifacts;

import java.util.Random;

public class ArtifactFactory {
    private static final Random random = new Random();

    private static final double MINIMUM_FOOD_VALUE = 1.0;
    private static final double MAXIMUM_FOOD_VALUE = 2.0;

    private static final String[] SUMMON_ARTIFACT_NAMES = new String[]{
            "Summoning Wand"};

    private static final String[] TRANSPORT_ARTIFACT_NAMES = new String[]{
            "Seven League Boots", "Talaria Winged Sandals"};

    private static final String[] CONCEALMENT_ARTIFACT_NAMES = new String[]{
            "Tarnhelm", "Helm of Darkness", "Cloak of Invisibility"};

    public Artifact createSummonArtifact(String name) {
        return new SummonArtifact(name);
    }

    public Artifact createSummonArtifact() {
        return createSummonArtifact(getRandomTransportArtifactName());
    }

    private static String getRandomSummonArtifactName() {
        return SUMMON_ARTIFACT_NAMES[random.nextInt(SUMMON_ARTIFACT_NAMES.length)];
    }

    public Artifact createTransportArtifact(String name) {
        return new TransportArtifact(name);
    }

    public Artifact createTransportArtifact() {
        return createTransportArtifact(getRandomTransportArtifactName());
    }

    private static String getRandomTransportArtifactName() {
        return TRANSPORT_ARTIFACT_NAMES[random.nextInt(TRANSPORT_ARTIFACT_NAMES.length)];
    }

    public Artifact createConcealmentArtifact(String name) {
        return new ConcealmentArtifact(name);
    }

    public Artifact createConcealmentArtifact() {
        return createConcealmentArtifact(getRandomConcealmentArtifactName());
    }

    private static String getRandomConcealmentArtifactName() {
        return CONCEALMENT_ARTIFACT_NAMES[random.nextInt(CONCEALMENT_ARTIFACT_NAMES.length)];
    }
}
