package turing.model;

public class DTM implements TuringMachine {
    public static final int ALPHABET_LENGTH = TuringMachine.LAST_CHAR - TuringMachine.FIRST_CHAR;
    private State[] states;
    private Tape[] tapes;
    private State currentState;
    
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
