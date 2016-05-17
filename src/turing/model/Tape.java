package turing.model;

import java.util.List;

public class Tape {
    protected List<Character> content;
    protected int positionPointer;

    void moveHead(Direction direction) {
        switch (direction) {
        case LEFT:
            positionPointer = positionPointer - 1;
            break;
        case RIGHT:
            positionPointer = positionPointer + 1;
            break;
        default:
            break;
        }
    }

    @Override
    public String toString() {
        StringBuilder TapeContent = new StringBuilder();
        for (Character symbol : content) {
            TapeContent.append(symbol);
        }
        return TapeContent.toString();
    }
}
