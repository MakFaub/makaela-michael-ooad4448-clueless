package clueless.cards;

import java.util.List;

public record Envelope(Card roomCard, Card suspectCard, Card weaponCard) {

    public boolean checkGuess(Card roomGuess, Card suspectGuess, Card weaponGuess) {
        return roomCard.getName().equals(roomGuess.getName())
                && suspectCard.getName().equals(suspectGuess.getName())
                && weaponCard.getName().equals(weaponGuess.getName());
    }

    public List<Card> getCards() {
        return List.of(roomCard, suspectCard, weaponCard);
    }
}
