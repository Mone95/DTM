package turing.model;

import java.util.ArrayList;

public class WorkingTape extends Tape {

    public WorkingTape(String content) {
        this.positionPointer = 0;
        this.content = new ArrayList<Character>();
        for (int i = 0; i < content.length(); i++) {
            this.content.add(content.charAt(i));
        }
    }

    char turingRead(Direction direction, char newSymbol) {
        if (this.positionPointer < 0) {
            this.content.add(0, newSymbol);
            this.positionPointer = 0;
            return TuringMachine.BLANK_CHAR;
        }
        if (this.positionPointer >= this.content.size()) {
            this.content.add(newSymbol);
            this.positionPointer = this.content.size() - 1;
            return TuringMachine.BLANK_CHAR;
        }
        char readSymbol = this.content.get(this.positionPointer);
        this.content.set(this.positionPointer, newSymbol);
        this.moveHead(direction);
        return readSymbol;
    }
}