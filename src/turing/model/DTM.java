package turing.model;

import java.util.Set;

/**
 * A class representing a deterministic Turing Machine and serving as an adapter
 * to its States and Commands
 */
public class DTM implements TuringMachine {
    
    /**
     * The length of the alphabet of the working tapes.
     */
    public static final int ALPHABET_LENGTH = TuringMachine.LAST_CHAR
            - TuringMachine.FIRST_CHAR + 1;
    private State startingState;
    private State[] states;
    private InputTape inputTape;
    private WorkingTape[] workingTapes;
    private State currentState;

    /**
     * Creates a new deterministic Turing Machine with {@code numberOfStates}
     * states, an input and an output tape, {@code numberOfTapes} working tapes,
     * the starting state given by {@code startStateId}, the stopping states 
     * given by {@code stopStateIds} and the accepting state given by 
     * {@code acceptStateIds}
     * 
     * @param numberOfStates The number of states the Turing Machine shall have.
     * @param numberOfTapes The number of tapes the Turing Machine shall have.
     * @param startStateId The number of the state from which should be started.
     * @param stopStateIds The states in which the Turing Machine should stop.
     * @param acceptStateIds The accepting states of the Turing Machine.
     */
    public DTM(int numberOfStates, int numberOfTapes, int startStateId,
            Set<Integer> stopStateIds, Set<Integer> acceptStateIds) {
        states = new State[numberOfStates];
        for (int i = 0; i < numberOfStates; i++) {
            if (stopStateIds.contains(new Integer(i))) {
                if (acceptStateIds.contains(new Integer(i))) {
                    states[i] = new State(i, true, true);
                } else {
                    states[i] = new State(i, true, false);
                }
            } else {
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
        for (int i = 0; i < this.workingTapes.length; i++) {
            this.workingTapes[i] = new WorkingTape();
        }
    }

    /**
     * Executes the given command by updating the configuration of the Turing 
     * Machine.
     * 
     * @param command The command to be executed.
     */
    private void executeCommand(Command command) {
        this.currentState = command.getTarget();
        this.inputTape.turingStep(command.getInputHeadDirection());
        char[] newTapeChars = command.getNewChars();
        Direction[] headDirections = command.getHeadDirections();
        for (int i = 0; i < headDirections.length; i++) {
            this.workingTapes[i].turingStep(headDirections[i], newTapeChars[i]);
        }
    }

    /**
     * Checks whether a letter is within the alphabet of the Turing Machine or 
     * not. Returns {@code true} if the letter is element of the alphabet and
     * {@code false} if not.
     * 
     * @param symbol The letter to be checked.
     * @return Returns whether the letter belongs to the tapes' alphabet or not.
     */
    public static boolean isValidTapeChar(char symbol) {
        if (symbol >= TuringMachine.FIRST_CHAR
                && symbol <= TuringMachine.LAST_CHAR
                || symbol == TuringMachine.BLANK_CHAR) {
            return true;
        }
        return false;
    }

    /**
     * Returns {@code true} if the command specified by the arguments is
     * compatible with this Turing Machine and {@code false} if not.
     * @param sourceState The state in which this command can be called
     * @param inputTapeChar The letter written on the input tape.
     * @param tapeChars The letters currently on the working tapes.
     * @param targetState The state the command goes to.
     * @param newTapeChars The letters that are written on the tapes.
     * @param tapeHeadMoves The directions to move the heads.
     * @return Whether the command is compatible with this DTM or not.
     */
    private boolean isValidCommmand(int sourceState, char inputTapeChar,
            char[] tapeChars, int targetState, char[] newTapeChars,
            Direction[] tapeHeadMoves) {
        if (sourceState < 0 || sourceState >= states.length || targetState < 0
                || targetState >= states.length) {
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
            if (!DTM.isValidTapeChar(newTapeChar)) {
                return false;
            }
        }
        if (!(tapeChars.length == newTapeChars.length)) {
            return false;
        }
        if (!(tapeChars.length == tapeHeadMoves.length)) {
            return false;
        }
        if (!(tapeChars.length == workingTapes.length)) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCommand(int sourceState, char inputTapeChar,
            char[] tapeChars, int targetState, Direction inputTapeHeadMove,
            char[] newTapeChars, Direction[] tapeHeadMoves) {
        if (!this.isValidCommmand(sourceState, inputTapeChar, tapeChars,
                targetState, newTapeChars, tapeHeadMoves)) {
            throw new IllegalArgumentException(
                    "Tried to add an invalid command!");
        }
        states[sourceState].addCommand(states[targetState], inputTapeChar,
                inputTapeHeadMove, tapeChars, newTapeChars, tapeHeadMoves);
    }

    /**
     * Checks whether a command for the current configuration is present and
     * executes it if there is one. Returns {@code true} if a command has been
     * executed and {@code false} if not.
     * 
     * @return whether a command has been executed or not.
     */
    private boolean run() {
        char[] currentTapeChars = new char[workingTapes.length];
        for (int i = 0; i < workingTapes.length; i++) {
            currentTapeChars[i] = workingTapes[i].read();
        }
        Command command = currentState.getCommandForCurrentConfiguration(
                this.inputTape.read(), currentTapeChars);
        if (command == null) {
            return false;
        }
        this.executeCommand(command);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean check(String input) {
        this.reset();
        this.inputTape = new InputTape(input);
        boolean hasExecutedACommand = true;
        while (!this.currentState.isStoppingState() && hasExecutedACommand) {
            hasExecutedACommand = this.run();
        }
        return this.currentState.isAcceptingState();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String simulate(String input) {
        this.check(input);
        return workingTapes[0].toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder DTMAsText = new StringBuilder();
        for (State state : states) {
            DTMAsText.append(state);
        }
        return DTMAsText.toString();
    }
}
