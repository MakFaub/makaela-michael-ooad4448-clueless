package clueless.strategy;

import clueless.Player;
import clueless.board.Space;
import clueless.commands.ICommand;

public interface IStrategy {
    ICommand selectAction(Player myself, Space space);
}