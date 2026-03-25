package clueless.artifacts;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtifactFactoryTest {

    private final ArtifactFactory factory = new ArtifactFactory();

    @Test
    void testCreateSummonArtifactWithName() {
        String name = "Summoning Artifact";

        Artifact artifact = factory.createSummonArtifact(name);

        assertNotNull(artifact);
        assertInstanceOf(SummonArtifact.class, artifact);
    }

    @Test
    void testCreateTransportArtifactWithName() {
        String name = "Transportation Artifact";

        Artifact artifact = factory.createTransportArtifact(name);

        assertNotNull(artifact);
        assertInstanceOf(TransportArtifact.class, artifact);
    }

    @Test
    void testCreateConcealmentArtifactWithName() {
        String name = "Concealment Artifact";

        Artifact artifact = factory.createConcealmentArtifact(name);

        assertNotNull(artifact);
        assertInstanceOf(ConcealmentArtifact.class, artifact);
    }

    @Test
    void testCreateSummonArtifact() {
        Artifact artifact = factory.createSummonArtifact();

        assertNotNull(artifact);
        assertInstanceOf(SummonArtifact.class, artifact);

        String name = ((SummonArtifact) artifact).getName();
        assertEquals("Summoning Wand", name);
    }

    @RepeatedTest(2)
    void testCreateTransportArtifact() {
        Artifact artifact = factory.createTransportArtifact();

        assertNotNull(artifact);
        assertInstanceOf(TransportArtifact.class, artifact);

        String name = ((TransportArtifact) artifact).getName();
        assertTrue(name.equals("Seven League Boots") || name.equals("Talaria Winged Sandals"));
    }

    @RepeatedTest(3)
    void testCreateConcealmentArtifact() {
        Artifact artifact = factory.createConcealmentArtifact();

        assertNotNull(artifact);
        assertInstanceOf(ConcealmentArtifact.class, artifact);

        String name = ((ConcealmentArtifact) artifact).getName();
        assertTrue(name.equals("Tarnhelm") || name.equals("Helm of Darkness") || name.equals("Cloak of Invisibility"));
    }
}
