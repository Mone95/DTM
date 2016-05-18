package turing.model;

import java.util.List;

public class Tape {
    protected List<Character> content;
    protected int positionPointer;

    protected char read() {
        if (this.positionPointer < 0
                || this.positionPointer >= this.content.size()) {
            return TuringMachine.BLANK_CHAR;
        }
        return this.content.get(this.positionPointer);
    }

    protected void write(char symbol) {
        if (this.positionPointer < 0) {
            this.content.add(0, symbol);
            this.positionPointer = 0;
        } else {
            int contentSize = this.content.size();
            if (this.positionPointer >= contentSize) {
                this.content.add(symbol);
            } else {
                this.content.set(this.positionPointer, symbol);
            }
        }
    }

    protected void moveHead(Direction direction) {
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