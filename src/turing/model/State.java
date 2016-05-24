package turing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An adapter for the commands of the Turing Machine.
 */
public class State {
    private final int NUMBEROFSTATE;
    private final boolean ISSTOPPING;
    private final boolean ISACCEPTING;
    private List<List<Command>> outgoingCommands = new ArrayList<List<Command>>(
            DTM.ALPHABET_LENGTH + 1);

    /**
     * Creates a new state with ID {@code NUMBEROFSTATE}, that is a stopping
     * state if {@code ISSTOPPINGState} is {@code true} and an accepting state
     * if additionally {@code ISACCEPTINGState} is {@code true}.
     * 
     * @param NUMBEROFSTATE The ID the state will have.
     * @param ISSTOPPINGState Determines whether the state is stopping or not.
     * @param ISACCEPTINGState Determines whether the state is accepting or not.
     */
    public State(int numberOfState, boolean isStoppingState,
            boolean isAcceptingState) {
        this.NUMBEROFSTATE = numberOfState;
        this.ISSTOPPING = isStoppingState;
        this.ISACCEPTING = isAcceptingState;
        for (int i = 0; i < DTM.ALPHABET_LENGTH + 1; i++) {
            outgoingCommands.add(new ArrayList<Command>());
        }
    }

    /**
     * Returns the ID of the state.
     * 
     * @return the ID of the state.
     */
    int getNumberOfState() {
        return this.NUMBEROFSTATE;
    }

    /**
     * Returns {@code true} if the state is a stopping state and {@code false} 
     * if not.
     * 
     * @return whether the state is a stopping state or not.
     */
    boolean isStoppingState() {
        return this.ISSTOPPING;
    }

    /**
     * Returns {@code true} if the state is an accepting state and {@code false} 
     * if not.
     * 
     * @return whether the state is an accepting state or not.
     */
    boolean isAcceptingState() {
        return this.ISACCEPTING;
    }
    
    /**
     * Returns the position of the list of commands with the inputTapeChar 
     * {@code c} within the list of outgoing commands of the state.
     * 
     * @param c The letter thats position should be given.
     * @return the position of the command list with the given input tape char.
     */
    private static int getPositionInList(char c) {
        if ((c < TuringMachine.FIRST_CHAR || c > TuringMachine.LAST_CHAR)
                && c != TuringMachine.BLANK_CHAR) {
            throw new IllegalArgumentException("A symbol that is not in the "
                    + "alphabet has been given to a tape");
        }
        return c == DTM.BLANK_CHAR ? DTM.ALPHABET_LENGTH : c - DTM.FIRST_CHAR;
    }

    /**
     * Returns the command that belongs to the current configuration of the the 
     * turing machine. Returns {@code null} if there is no such command.
     * 
     * @param InputChar The char currently on the input tape.
     * @param WorkingTapeChars The letters currently on the working tapes.
     * @return the command determined by the current configuration or null.
     */
    Command getCommandForCurrentConfiguration(char inputChar,
            char[] workingTapeChars) {
        List<Command> listOfCommands = outgoingCommands
                .get(getPositionInList(inputChar));
        int indexInList = listOfCommands.indexOf(
                Command.getSearchDummy(this, inputChar, workingTapeChars));
        return indexInList == -1 ? null : listOfCommands.get(indexInList);
    }

    /**
     * Adds a new Command with this state as its source if there is none for the
     * given configuration.
     * 
     * @param target The target state.
     * @param inputChar The letter on the input tape.
     * @param inputHeadDirection The direction to move the head on the input 
     *        tape to.
     * @param commandChars The letters currently on the working tapes.
     * @param newChars The letters to be written on the working tapes.
     * @param directions The directions to move the heads of the working tapes.
     */
    void addCommand(State target, char inputChar, Direction inputHeadDirection,
            char[] commandChars, char[] newChars, Direction[] directions) {
        Command newCommand = new Command(this, target, inputChar,
                inputHeadDirection, commandChars, newChars, directions);
        List<Command> commandList = outgoingCommands
                .get(State.getPositionInList(inputChar));
        if (!commandList.contains(newCommand)) {
            commandList.add(newCommand);
        }
    }

    /**
     * Gives a String representation of the outgoing commands of this state 
     * ordered lexicographically.
     */
    @Override
    public String toString() {
        for (List<Command> commands : this.outgoingCommands) {
            Collections.sort(commands);
        }
        StringBuilder stateAsString = new StringBuilder();
        for (List<Command> commands : outgoingCommands) {
            for (Command command : commands) {
                stateAsString.append(command);
                stateAsString.append("\n");
            }
        }
        return stateAsString.toString();
    }
}