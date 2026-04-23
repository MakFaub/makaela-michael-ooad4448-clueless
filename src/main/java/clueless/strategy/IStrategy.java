package clueless.strategy;

import clueless.board.Space;
import clueless.commands.ICommand;

public interface IStrategy {
    ICommand selectAction(Character myself, Space space);
}