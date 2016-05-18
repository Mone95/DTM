package turing.model;

import java.util.ArrayList;

public class InputTape extends Tape {

    public InputTape(String content) {
        this.positionPointer = 0;
        this.content = new ArrayList<Character>();
        for (int i = 0; i < content.length(); i++) {
            this.content.add(content.charAt(i));
        }
    }

    char turingRead(Direction direction) {
        if (this.positionPointer < 0
                || this.positionPointer >= this.content.size()) {
            this.moveHead(direction);
            return TuringMachine.BLANK_CHAR;
        } else {
            char readSymbol = this.content.get(this.positionPointer);
            this.moveHead(direction);
            return readSymbol;
        }
    }
}