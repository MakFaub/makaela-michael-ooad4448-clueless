package clueless;

import clueless.board.Board;
import clueless.board.Direction;
import clueless.board.Space;
import clueless.cards.Deck;
import clueless.cards.Envelope;
import clueless.pieces.Piece;
import clueless.commands.*;

import java.util.ArrayList;
import java.util.List;

public class Clueless {
    private Board board;
    private Deck deck;
    private Envelope envelope;
    private List<Player> players;
    private List<Piece> pieces;


    public Clueless(Board board, Deck deck, List<Player> players, List<Piece> pieces, Envelope envelope) {

    }

    private void setup() {
        board = new Board.Builder().createBasicBoard().build();
        deck.shuffle();
        envelope = deck.fillEnvelope();
        deck.deal(players);
    }

    private void reset() {
        setup();
    }

    public boolean isOver() {
        // TODO if playersLeft == 0 || winner != null
        return true;
    }

    public Player getCurrentPlayer() {
        // TODO gets the current player
        return null;
    }

    public void nextPlayer() {
        // TODO changes to next player in list (if at the end of the list goes to the start
    }

    // This is already implemented in PlayerStrategy
    public List<ICommand> getPossibleCommands(Player player) {
        List<ICommand> commands = new ArrayList<>();

        // if(canMove(player)) commands.add(new MoveCommand(player,board));

        // if(canCheck(player)) commands.add(new CheckCommand(player,board));

        // if(canSuggest(player)) commands.add(new SuggestCommand(player,board));

        // if(canAccuse(player)) commands.add(new AccuseCommand(player,board));

        // if(canTake(player)) commands.add(new TakeCommand(player,board));

        // if(canSummon(player)) commands.add(new SummonCommand(player,board));

        // if(canTransport(player)) commands.add(new TransportCommand(player,board));

        // if(canConceal(player)) commands.add(new ConcealCommand(player, board));

        return commands;
    }

    private void play() {
        while (!isOver()) {
            Player currentPlayer = getCurrentPlayer();

            //List<Command> possibleCommands = getPossibleCommands(currentPlayer);

            //Command selectedAction = currentPlayer.selectCommand(possibleCommands);

            //selectedAction.execute();

            //nextPlayer();
        }
    }
}