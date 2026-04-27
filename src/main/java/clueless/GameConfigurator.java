package clueless;

import clueless.board.Board;
import clueless.cards.CardFactory;
import clueless.cards.Deck;
import clueless.pieces.IPiece;
import clueless.pieces.PieceFactory;
import clueless.pieces.SuspectPiece;

import java.util.ArrayList;
import java.util.List;

public class GameConfigurator {
    static PieceFactory pieceFactory = new PieceFactory();
    static CardFactory cardFactory = new CardFactory();

    Board.Builder boardBuilder;
    int numPlayers;

    public GameConfigurator(int numPlayers) {
        this.numPlayers = numPlayers;
        this.boardBuilder = new Board.Builder()
                .createDefaultClueBoard()
                .placePieces(pieceFactory);
    }

    public Clueless build() {
        Board board = boardBuilder.build();
        Deck deck = new Deck(cardFactory);

        List<IPiece> suspects = new ArrayList<>(board.getAllSuspectPieces());
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            Player p = new Player("Player " + (i + 1));
            p.assignPlayerPiece((SuspectPiece) suspects.get(i));
            players.add(p);
        }
        return new Clueless(board, deck, players);
    }

    public static void main(String[] args) {
        int numPlayers = args.length > 0 ? Integer.parseInt(args[0]) : 4;
        new GameConfigurator(numPlayers).build().play();
    }
}