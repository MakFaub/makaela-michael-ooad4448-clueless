package clueless.commands;

public interface ICommand {
    boolean execute();

    CommandType getType();

    String optionString();
}