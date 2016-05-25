package turing.model;

import java.util.List;

/**
 * A class to represent general aspects of tapes offering all the functionality
 * necessary for specialized tapes
 */
public class Tape {
    /**
     * The letters currently on the tape.
     */
    protected List<Character> content;
    
    /**
     * The position of the head.
     */
    protected int positionPointer;
    
    /**
     * Removes every blank char that comes before any letter on the tape.
     */
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
    
    /**
     * Removes any blank that comes after the last letter on the tape.
     */
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

    /**
     * Returns the letter that is under the head right now.
     * 
     * @return the letter under the head.
     */
    protected char read() {
        if (this.positionPointer < 0
                || this.positionPointer >= this.content.size()) {
            return TuringMachine.BLANK_CHAR;
        }
        return this.content.get(this.positionPointer);
    }

    /**
     * Writes the given letter onto the position of the tape the head currently
     * is at.
     * 
     * @param symbol The letter to be written onto the tape.
     */
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

    /**
     * Moves the head into the given direction.
     * 
     * @param direction The direction to move the head into.
     */
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

    /**
     * Returns a textual representation of the content of the tape.
     * 
     * @return a String representation of the content of the tape. 
     */
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