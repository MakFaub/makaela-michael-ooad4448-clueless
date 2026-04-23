package clueless;

import clueless.board.Space;
import clueless.cards.Card;
import clueless.cards.WeaponCard;
import clueless.pieces.ArtifactPiece;
import clueless.pieces.ConcealmentArtifact;
import clueless.pieces.SummonArtifact;
import clueless.pieces.TransportArtifact;

import clueless.pieces.Piece;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {
    private final String name;
    private final List<Card> hand = new ArrayList<>();
    private Piece suspectPiece;
    private Space currentSpace;

    public void setPiece(Piece piece) {
        this.suspectPiece = piece;
    }

    public Piece getPiece() {
        return this.suspectPiece;
    }

    public Space getCurrentSpace() {
        return this.currentSpace;
    }



    private final List<Card> discoveredCards = new ArrayList<>();
    private final Set<Card> hand = new HashSet<>();
    private final Set<Card> discoveredCards = new HashSet<>();
    private Set<ArtifactPiece> artifacts = new HashSet<>();

    public Player(String name){ this.name = name; }

    public String getName(){ return name; }

    public void discoverCard(Card card) { discoveredCards.add(card); }

    public Set<Card> getDiscoveredCards() { return discoveredCards; }

    public Set<Card> getHand() { return hand; }

    public void addCardsToHand(List<Card> cards) {
        hand.addAll(cards);
        discoveredCards.addAll(cards);
    }

    public Set<ArtifactPiece> getArtifacts() { return artifacts; }

    public boolean hasTransportArtifact() {
        return artifacts.stream().anyMatch(artifact -> artifact instanceof TransportArtifact);
    }

    public boolean hasConcealmentArtifact() {
        return artifacts.stream().anyMatch(artifact -> artifact instanceof ConcealmentArtifact);
    }

    public boolean hasSummonArtifact() {
        return artifacts.stream().anyMatch(artifact -> artifact instanceof SummonArtifact);
    }

    public void useArtifact(ArtifactPiece artifact) { artifacts.remove(artifact); }
}
