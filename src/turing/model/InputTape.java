package turing.model;

import java.util.ArrayList;

public class InputTape extends Tape {

    public InputTape() {
        this.positionPointer = 0;
        this.content = new ArrayList<Character>();
    }

    public InputTape(String content) {
        this.positionPointer = 0;
        this.content = new ArrayList<Character>();
        for (int i = 0; i < content.length(); i++) {
            this.content.add(content.charAt(i));
        }
    }

    char turingStep(Direction direction) {
        char result = this.read();
        this.moveHead(direction);
        return result;
    }
}