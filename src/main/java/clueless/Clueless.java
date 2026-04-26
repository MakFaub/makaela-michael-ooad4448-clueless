package clueless;

import clueless.board.Board;
import clueless.board.Direction;
import clueless.board.Space;
import clueless.cards.Deck;
import clueless.cards.Envelope;
import clueless.pieces.Piece;
import clueless.commands.*;
import clueless.strategy.PlayerStrategy;

import java.util.ArrayList;
import java.util.List;

public class Clueless {
    private Board board;
    private final Deck deck;
    private Envelope envelope;
    private final List<Player> players;
    private final List<Player> activePlayers;
    private int currentPlayerIndex = 0;
    boolean envelopeGuessed = false;

    private final PlayerStrategy playerStrategy;

    public Clueless(Board board, Deck deck, List<Player> players) {
        this.board = board;
        this.deck = deck;
        this.players = players;
        this.activePlayers = new ArrayList<>(players);
        this.playerStrategy = new PlayerStrategy(players, board, envelope);
    }

    private void setup() {
        deck.shuffle();
        envelope = deck.fillEnvelope();
        deck.deal(players);
    }

    public Player getCurrentPlayer() {
        return activePlayers.get(currentPlayerIndex);
    }

    protected void playTurn() {
        Player currentPlayer = getCurrentPlayer();
        Space currentSpace = board.getSpaceBasedOnPiece(currentPlayer.getPlayerPiece());

        ICommand action = playerStrategy.selectAction(currentPlayer, currentSpace);
        boolean result = action.execute();

        if (action.getType() == CommandType.ACCUSE) {
            if (result) {
                envelopeGuessed = true;
            } else {
                System.out.println(currentPlayer.getName() + " your guess was wrong! You are eliminated.");
                activePlayers.remove(currentPlayer);
                if (currentPlayerIndex >= activePlayers.size()) {
                    currentPlayerIndex = 0;
                }
            }
        } else {
            currentPlayerIndex = (currentPlayerIndex + 1) % activePlayers.size();
        }
    }

    void play() {
        setup();
        while (!envelopeGuessed && activePlayers.size() > 1) {
            playTurn();
        }
        if (envelopeGuessed) {
            System.out.println(getCurrentPlayer().getName() + " wins!");
        } else if (activePlayers.size() == 1) {
            System.out.println(activePlayers.getFirst().getName() + " wins by default!");
        }
    }
}