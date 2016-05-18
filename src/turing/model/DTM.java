package turing.model;

import java.util.Set;

public class DTM implements TuringMachine {
    public static final int ALPHABET_LENGTH = TuringMachine.LAST_CHAR - TuringMachine.FIRST_CHAR;
    private State[] states;
    private Tape[] tapes;
    private State currentState;
    
    public static boolean isValidTapeChar(char symbol) {
        if (symbol >= TuringMachine.FIRST_CHAR && symbol <= TuringMachine.LAST_CHAR || symbol == TuringMachine.BLANK_CHAR) {
            return true;
        }
        return false;
    }
    
    public DTM(int numberOfStates, int numberOfTapes, int startStateId,
            Set<Integer> stopStateIds, Set<Integer> acceptStateIds) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addCommand(int sourceState, char inputTapeChar,
            char[] tapeChars, int targetState, Direction inputTapeHeadMove,
            char[] newTapeChars, Direction[] tapeHeadMoves) {
        // TODO Auto-generated method stub
        
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
