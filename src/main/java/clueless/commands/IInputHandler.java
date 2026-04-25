package clueless.commands;

import java.util.List;

@FunctionalInterface
public interface IInputHandler {
    <T> T choose(List<T> options);
}