package turing.model;

import java.util.ArrayList;

/**
 * A class to hold the information the Turing Machine needs to choose the next
 * command and to offer a way to execute it.
 */
public class WorkingTape extends Tape {
    /**
     * Creates a new working tape that is empty.
     */
    public WorkingTape() {
        this.positionPointer = 0;
        this.content = new ArrayList<Character>();
    }

    /**
     * Creates a new working tape that has {@code content} on it.
     * 
     * @param content The content of the working tape.
     */
    public WorkingTape(String content) {
        this.positionPointer = 0;
        this.content = new ArrayList<Character>();
        for (int i = 0; i < content.length(); i++) {
            this.content.add(content.charAt(i));
        }
    }

    /**
     * Reads the letter that currently is under the head of the working tape,
     * writes {@code newSymbol} at that position and moves the head into the
     * given direction afterwards.
     * 
     * @param direction The direction to move the head into.
     * @param newSymbol The letter to be written onto the tape.
     * @return the letter that currently is under the head of the tape.
     */
    char turingStep(Direction direction, char newSymbol) {
        char result = this.read();
        this.write(newSymbol);
        this.moveHead(direction);
        return result;
    }
}