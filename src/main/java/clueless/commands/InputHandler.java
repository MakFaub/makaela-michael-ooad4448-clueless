package clueless.commands;

import java.util.List;

@FunctionalInterface
public interface InputHandler {
    <T> T choose(List<T> options);
}