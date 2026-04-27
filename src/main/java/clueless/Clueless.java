package clueless;

import clueless.board.Board;
import clueless.board.BoardDisplay;
import clueless.board.Space;
import clueless.cards.Deck;
import clueless.cards.Envelope;
import clueless.commands.*;
import clueless.strategy.PlayerStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Clueless {
    private static final Logger logger = Logger.getLogger(Clueless.class.getName());


    private final Board board;
    private final Deck deck;
    private final List<Player> players;
    private final List<Player> activePlayers;

    private PlayerStrategy playerStrategy;
    private Envelope envelope;
    private int currentPlayerIndex = 0;
    private boolean envelopeGuessed = false;


    public Clueless(Board board, Deck deck, List<Player> players) {
        this.board = board;
        this.deck = deck;
        this.players = players;
        this.activePlayers = new ArrayList<>(players);
    }

    private void setup() {
        deck.shuffle();
        envelope = deck.fillEnvelope();
        deck.deal(players);
        playerStrategy = new PlayerStrategy(players,board,envelope);
    }

    public Player getCurrentPlayer() {
        return activePlayers.get(currentPlayerIndex);
    }

    protected void playTurn() {
        Player currentPlayer = getCurrentPlayer();
        Space currentSpace = board.getSpaceBasedOnPiece(currentPlayer.getPlayerPiece());

        System.out.println("==== " + currentPlayer.getName() + "'s turn ====");
        System.out.println(BoardDisplay.render(board, activePlayers));
        ICommand action = playerStrategy.selectAction(currentPlayer, currentSpace);
        boolean result = action.execute();

        if (action.getType() == CommandType.ACCUSE) {
            if (result) {
                envelopeGuessed = true;
                logger.info(currentPlayer.getName() + " has solved the murder!");
            } else {
                logger.info(currentPlayer.getName() + " your guess was wrong! You are eliminated.");
                activePlayers.remove(currentPlayer);
                if (currentPlayerIndex >= activePlayers.size()) currentPlayerIndex = 0;
            }
        } else {
            logger.info(currentPlayer.getName() + " performed a " + action.getType() + " action.");
            currentPlayerIndex = (currentPlayerIndex + 1) % activePlayers.size();
        }
    }

    public void play() {
        logger.info("Game started with " + activePlayers.size() + " players.");
        setup();
        while (!envelopeGuessed && activePlayers.size() > 1) {
            playTurn();
        }
        if (envelopeGuessed) {
            logger.info(getCurrentPlayer().getName() + " wins!");
        } else if (activePlayers.size() == 1) {
            logger.info(activePlayers.getFirst().getName() + " wins by default!");
        }
    }
}