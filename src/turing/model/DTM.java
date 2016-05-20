package turing.model;

import java.util.Set;

public class DTM implements TuringMachine {
    public static final int ALPHABET_LENGTH = TuringMachine.LAST_CHAR - TuringMachine.FIRST_CHAR + 1;
    private State startingState;
    private State[] states;
    private InputTape inputTape;
    private WorkingTape[] workingTapes;
    private State currentState;
    
    public DTM(int numberOfStates, int numberOfTapes, int startStateId,
            Set<Integer> stopStateIds, Set<Integer> acceptStateIds) {
        states = new State[numberOfStates];
        for (int i = 0; i < numberOfStates; i++) {
            if (stopStateIds.contains(new Integer(i))) {
                if (acceptStateIds.contains(new Integer(i))) {
                    states[i] = new State (i, true, true);
                }
                else {
                    states[i] = new State (i, true, false);
                }
            }
            else {
            states[i] = new State(i, false, false);
            }
        }
        startingState = states[startStateId];
        this.workingTapes = new WorkingTape[numberOfTapes + 1];
        for (int i = 0; i < numberOfTapes + 1; i++) {
            this.workingTapes[i] = new WorkingTape();
        }
        
    }
    
    private void reset() {
        this.currentState = this.startingState;
        for(WorkingTape workingTape : this.workingTapes) {
            workingTape = new WorkingTape();
        }
    }
    
    public static boolean isValidTapeChar(char symbol) {
        if (symbol >= TuringMachine.FIRST_CHAR && symbol <= TuringMachine.LAST_CHAR || symbol == TuringMachine.BLANK_CHAR) {
            return true;
        }
        return false;
    }
    
    private boolean isValidCommmand (int sourceState, char inputTapeChar,
            char[] tapeChars, int targetState, char[] newTapeChars, Direction[] tapeHeadMoves) {
        if (sourceState < 0 || sourceState >= states.length || targetState < 0 || targetState >= states.length) {
            return false;
        }
        if (!DTM.isValidTapeChar(inputTapeChar)) {
            return false;
        }
        for (char tapeChar : tapeChars) {
            if (!DTM.isValidTapeChar(tapeChar)) {
                return false;
            }
        }
        for (char newTapeChar : newTapeChars) {
            if(!DTM.isValidTapeChar(newTapeChar)) {
                return false;
            }
        }
        if (!(tapeChars.length == newTapeChars.length)) {
            return false;
        }
        if (!(tapeChars.length == tapeHeadMoves.length)) {
            return false;
        }
        if(!(tapeChars.length == workingTapes.length)) {
            return false;
        }
        return true;
    }
    
    @Override
    public void addCommand(int sourceState, char inputTapeChar,
            char[] tapeChars, int targetState, Direction inputTapeHeadMove,
            char[] newTapeChars, Direction[] tapeHeadMoves) {
        if (!this.isValidCommmand(sourceState, inputTapeChar, tapeChars, targetState, newTapeChars, tapeHeadMoves)) {
            throw new IllegalArgumentException("Tried to add an invalid command!");
        }
        states[sourceState].addCommand(states[targetState], inputTapeChar, inputTapeHeadMove, tapeChars, newTapeChars, tapeHeadMoves);
    }
    
    @Override
    public String simulate(String input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean check(String input) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String toString() {
        StringBuilder DTMAsText = new StringBuilder();
        for (State state : states) {
            DTMAsText.append(state);
        }
        return DTMAsText.toString();
    }
}
