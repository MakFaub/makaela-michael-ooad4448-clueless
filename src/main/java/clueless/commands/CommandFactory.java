package clueless.commands;

import clueless.Player;
import clueless.board.Space;
import clueless.pieces.IPiece;

public class CommandFactory {
    public ICommand newMoveCommand(Player player, Space space, Space newSpace) { return new MoveCommand(player, space, newSpace); }

    public ICommand newSuggestCommand(Player player, Space space) { return new SuggestCommand(player, space); }

    public ICommand newAccuseCommand(Player player, Space space) { return new AccuseCommand(player, space); }

    public ICommand newLookCommand(Player player, Space space) { return new LookCommand(player, space); }

    public ICommand newSummonCommand(Player player, Space space) { return new SummonCommand(player, space); }

    public ICommand newTransportCommand(Player player, Space space) {return new TransportCommand(player, space); }

    public ICommand newTakeCommand(Player player, Space space, IPiece piece, IInputHandler inputHandler) { return new TakeCommand(player, space, piece, inputHandler); }
}
