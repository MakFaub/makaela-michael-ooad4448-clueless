package clueless;

import clueless.board.*;
import clueless.cards.*;
import clueless.pieces.*;
import clueless.commands.*;
import clueless.die.*;
import clueless.strategy.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

public class Clueless {
    private final Board board;
    private final Deck deck;
    private final List<Piece> pieces;
    private final List<Player> players;
    private final List<Player> activePlayers;
    private final IStrategy strategy;
    private final Die die;

    private Envelope envelope;

    private int currentPlayerNum = 0;
    private Player winner = null;


    public Clueless(Board board, Deck deck, List<Player> players, List<Piece> pieces, IStrategy strategy, Die die) {
        this.board = board;
        this.deck = deck;
        this.pieces = pieces;
        this.players = players;
        this.activePlayers = new ArrayList<Player>(players);
        this.die = die;
        this.strategy = strategy;
    }

    private void setup() {
        deck.shuffle();
        envelope = deck.fillEnvelope();
        deck.deal(players);

        placePieces();
    }

    private List<Piece> getPiecesOfType(PieceType type) {
        return pieces.stream().filter(piece -> piece.isType(type)).collect(Collectors.toList());
    }

    private List<Piece> getArtifacts() {
        return pieces.stream().filter(piece -> piece.getType().isArtifact()).collect(Collectors.toList());
    }

    private void placePieces() {

        List<Space> startingSpaces = board.getStartingSpaces();
        List<Space> nonStartingSpaces = board.getNonStartingSpaces();

        placeSuspectPieces(getPiecesOfType(PieceType.Suspect), startingSpaces);
        placeWeaponPieces(getPiecesOfType(PieceType.Weapon), nonStartingSpaces);
        placeArtifactPieces(getArtifacts(), nonStartingSpaces);
    }

    private void placeSuspectPieces(List<Piece> suspectPieces, List<Space> startingSpaces) {
        for (int i = 0; i < suspectPieces.size(); i++) {
            Space space = startingSpaces.get(i);
            Piece suspect = suspectPieces.get(i);
            space.addPiece(suspect);

            for (Player player : players) {
                if(player.getPlayerPiece() == suspect) player.setCurrentSpace(space);
            }
        }
    }

    private void placeWeaponPieces(List<Piece> weaponPieces, List<Space> nonStartingSpaces) {
        for (int i = 0; i < weaponPieces.size(); i++) {
            nonStartingSpaces.get(i).addPiece(weaponPieces.get(i));
        }
    }

    private void placeArtifactPieces(List<Piece> artifacts, List<Space> nonStartingSpaces) {
        for (int i = 0; i < artifacts.size(); i++) {
            nonStartingSpaces.get(i).addPiece(artifacts.get(i));
        }
    }

    private void reset() {
        setup();
    }

    public boolean isOver() { return winner != null || activePlayers.isEmpty(); }


    public Player getCurrentPlayer() {
        if(activePlayers.isEmpty()) return null;
        return activePlayers.get(currentPlayerNum % activePlayers.size());
    }

    public void nextPlayer() {
        if (activePlayers.isEmpty()) return;
        currentPlayerNum = (currentPlayerNum + 1) % activePlayers.size();
    }

    public void eliminatePlayer(Player player) {
        int playerNum = activePlayers.indexOf(player);
        if(playerNum < 0) return;
        activePlayers.remove(playerNum);

        if(playerNum < currentPlayerNum) currentPlayerNum--;
        if(currentPlayerNum >= activePlayers.size() && !activePlayers.contains(player)) {
            currentPlayerNum = 0;
        }
    }

    public void setWinner(Player player) {this.winner = player;}

    public Envelope getEnvelope() {return envelope;}

    public Board getBoard() {return board;}

    public void play() {
        setup();
        while (!isOver()) {
            Player currentPlayer = getCurrentPlayer();
            Space currentSpace = currentPlayer.getCurrentSpace();

            ICommand selectedAction = strategy.selectAction(currentPlayer, currentSpace);
            boolean success = selectedAction.execute();

            if (selectedAction.getType() == CommandType.ACCUSE) {
                if (success) setWinner(currentPlayer);
                else eliminatePlayer(currentPlayer);
            } else {
                nextPlayer();
            }
        }
    }

    public  Player getWinner(){
        if(winner != null) return winner;
        else return null;
    }
}