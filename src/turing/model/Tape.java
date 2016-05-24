package turing.model;

import java.util.List;

public class Tape {
    protected List<Character> content;
    protected int positionPointer;
    
    private void removeBlanksAtBeginning () {
        int numberOfBlanksAtBeginning = 0;
        for (Character symbol : this.content) {
            if (symbol == TuringMachine.BLANK_CHAR) {
                numberOfBlanksAtBeginning++;
            } else {
                break;
            }
        }
        for (int i = 0; i < numberOfBlanksAtBeginning; i++) {
            this.content.remove(0);
        }
    }
    
    private void removeBlanksAtEnd () {
        int numberOfBlanksAtEnd = 0;
        for (int i = content.size() - 1; i >= 0; i--) {
            if (this.content.get(i) == TuringMachine.BLANK_CHAR) {
                numberOfBlanksAtEnd++;
            } else {
                break;
            }
        }
        for (int i = 0; i < numberOfBlanksAtEnd; i++) {
            this.content.remove(this.content.size() - 1);
        }
    }

    protected char read() {
        if (this.positionPointer < 0
                || this.positionPointer >= this.content.size()) {
            return TuringMachine.BLANK_CHAR;
        }
        return this.content.get(this.positionPointer);
    }

    protected void write(char symbol) {
        if (this.positionPointer < 0) {
            this.content.add(0,symbol);
            this.positionPointer = 0;
        }
        else if (this.positionPointer >= this.content.size()) {
            this.content.add(symbol);
        }
        else {
            this.content.set(this.positionPointer, symbol);
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
        this.removeBlanksAtBeginning();
        this.removeBlanksAtEnd();
        StringBuilder TapeContent = new StringBuilder("");
        for (Character symbol : content) {
            TapeContent.append(symbol);
        }
        return TapeContent.toString();
    }
}