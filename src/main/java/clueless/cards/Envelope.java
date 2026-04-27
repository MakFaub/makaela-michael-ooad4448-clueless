package clueless.cards;

import java.util.List;

public record Envelope(Card suspectCard, Card weaponCard, Card roomCard) {

    public boolean checkGuess(Card suspectGuess, Card weaponGuess, Card roomGuess) {
        return suspectCard.getName().equals(suspectGuess.getName())
                && weaponCard.getName().equals(weaponGuess.getName())
                && roomCard.getName().equals(roomGuess.getName());
    }

    public List<Card> getCards() {
        return List.of(suspectCard, weaponCard, roomCard);
    }
}
