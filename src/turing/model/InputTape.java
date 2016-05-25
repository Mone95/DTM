package turing.model;

import java.util.ArrayList;

/**
 * A class to hold the information the Turing Machine needs to choose the next
 * command and to offer a way to execute it.
 */
public class InputTape extends Tape {

    /**
     * Creates a new input tape that is empty.
     */
    public InputTape() {
        this.positionPointer = 0;
        this.content = new ArrayList<Character>();
    }

    /**
     * Creates a new input tape that has {@code content} on it.
     * 
     * @param content The content of the input tape.
     */
    public InputTape(String content) {
        this.positionPointer = 0;
        this.content = new ArrayList<Character>();
        for (int i = 0; i < content.length(); i++) {
            this.content.add(content.charAt(i));
        }
    }

    /**
     * Reads a letter and moves the head into the given direction.
     * 
     * @param direction The direction to move the head into.
     * @return the letter currently under the head.
     */
    char turingStep(Direction direction) {
        char result = this.read();
        this.moveHead(direction);
        return result;
    }
}