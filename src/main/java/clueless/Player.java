package clueless;

import clueless.cards.Card;
import clueless.cards.WeaponCard;
import clueless.pieces.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {
    private final String name;
    private SuspectPiece playerPiece;

    private final Set<Card> hand = new HashSet<>();
    private final Set<Card> discoveredCards = new HashSet<>();
    private Set<IPiece> piecesInHand = new HashSet<>();

    // TODO: change constructor to assign playerPiece and remove assignPlayerPiece
    public Player(String name){ this.name = name; }

    public void assignPlayerPiece(SuspectPiece piece){ this.playerPiece = piece; }

    public SuspectPiece getPlayerPiece() { return playerPiece; }

    public String getName(){ return name; }

    public void discoverCard(Card card) { discoveredCards.add(card); }

    public Set<Card> getDiscoveredCards() { return discoveredCards; }

    public Set<Card> getHand() { return hand; }

    public void addCardsToHand(List<Card> cards) {
        hand.addAll(cards);
        discoveredCards.addAll(cards);
    }

    public void takePiece(IPiece piece) { piecesInHand.add(piece); }

    public Set<IPiece> getPiecesInHand() { return piecesInHand; }

    public boolean hasPieceOfType(PieceType type) { return piecesInHand.stream().anyMatch(piece -> piece.getType() == type); }

    public boolean hasArtifact() {
        return piecesInHand.stream().anyMatch(piece -> piece.getType().isArtifact());
    }

    public boolean hasTransportArtifact() {
        return hasPieceOfType(PieceType.Transport);
    }

    public boolean hasConcealmentArtifact() {
        return hasPieceOfType(PieceType.Conceal);
    }

    public boolean hasSummonArtifact() {
        return hasPieceOfType(PieceType.Summon);
    }

    public boolean hasWeaponPiece() {
        return hasPieceOfType(PieceType.Weapon);
    }

    public IPiece getPieceOfType(PieceType type) {
        return piecesInHand.stream().filter(p -> p.getType() == type).findFirst().orElse(null);
    }

    public void swapPiecesInHand(IPiece oldPiece, IPiece newPiece) {
        piecesInHand.remove(oldPiece);
        piecesInHand.add(newPiece);
    }
}
