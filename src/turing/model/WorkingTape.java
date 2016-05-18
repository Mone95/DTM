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

    char turingStep(Direction direction, char newSymbol) {
        char result = this.read();
        this.write(newSymbol);
        this.moveHead(direction);
        return result;
    }
}