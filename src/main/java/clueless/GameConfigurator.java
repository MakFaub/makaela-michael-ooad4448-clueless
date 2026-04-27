package clueless;

import clueless.board.Board;
import clueless.cards.CardFactory;
import clueless.cards.Deck;
import clueless.pieces.IPiece;
import clueless.pieces.PieceFactory;
import clueless.pieces.SuspectPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public ObservableClueless build() {
        Board board = boardBuilder.build();
        Deck deck = new Deck(cardFactory);

        List<IPiece> suspects = new ArrayList<>(board.getAllSuspectPieces());
        List<Player> players = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < numPlayers; i++) {
            IPiece chosen = promptForSuspect(i + 1, suspects, scanner);
            Player p = new Player("Player " + (i + 1));
            p.assignPlayerPiece((SuspectPiece) chosen);
            players.add(p);
        }

        ObservableClueless game = new ObservableClueless(board, deck, players);
        EventBus.getInstance().attach(game);
        return game;
    }

    private IPiece promptForSuspect(int playerNum, List<IPiece> available, Scanner scanner) {
        System.out.println("\nPlayer " + playerNum + ", pick your suspect:");
        for (int j = 0; j < available.size(); j++) {
            System.out.println((j + 1) + ". " + available.get(j).getName());
        }

        while (true) {
            System.out.print("Enter number: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= available.size()) {
                    return available.remove(choice - 1);
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Invalid choice.");
        }
    }

    public static void main(String[] args) {
        final int DEFAULT_PLAYER_COUNT = 2;
        int numPlayers = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PLAYER_COUNT;
        new GameConfigurator(numPlayers).build().play();
    }
}