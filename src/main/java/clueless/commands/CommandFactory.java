package clueless.commands;

import clueless.Player;
import clueless.board.Board;
import clueless.board.Room;
import clueless.board.Space;
import clueless.pieces.IPiece;

public class CommandFactory {
    public ICommand newMoveCommand(Player player, Space space, Space newSpace) { return new MoveCommand(player, space, newSpace); }

    public ICommand newSuggestCommand(Player player, Space space, Board board, IInputHandler inputHandler) { return new SuggestCommand(player, space, board, inputHandler); }

    public ICommand newAccuseCommand(Player player, Space space) { return new AccuseCommand(player, space); }

    public ICommand newLookCommand(Player player, Player otherPlayer, Space space) { return new LookCommand(player, otherPlayer, space); }

    public ICommand newSummonCommand(Player player, Space space, IPiece weapon, Room weaponRoom) { return new SummonCommand(player, space, weapon, weaponRoom); }

    public ICommand newTransportCommand(Player player, Space space, Room room) {return new TransportCommand(player, space, room); }

    public ICommand newTakeCommand(Player player, Space space, IPiece piece, IInputHandler inputHandler) { return new TakeCommand(player, space, piece, inputHandler); }
}
