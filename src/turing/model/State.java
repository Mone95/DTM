package turing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class State {
    private final int numberOfState;
    private final boolean isStopping;
    private final boolean isAccepting;
    private List<List<Command>> outgoingCommands =
            new ArrayList<List<Command>>(DTM.ALPHABET_LENGTH);

    public State(int numberOfState, boolean isStoppingState,
            boolean isAcceptingState) {
        this.numberOfState = numberOfState;
        this.isStopping = isStoppingState;
        this.isAccepting = isAcceptingState;
        for (int i = 0; i < DTM.ALPHABET_LENGTH; i++) {
            outgoingCommands.add(new ArrayList<Command>());
        }
    }

    int getNumberOfState() {
        return this.numberOfState;
    }

    boolean isStoppingState() {
        return this.isStopping;
    }

    boolean isAcceptingState() {
        return this.isAccepting;
    }

    private List<Command> getCommandsOfInputSymbol(char symbol) {
        if (symbol < TuringMachine.FIRST_CHAR
                || symbol > TuringMachine.LAST_CHAR) {
            throw new IllegalArgumentException(
                    "A symbol that is not in the Alphabet has been given to "
                            + "the Input Tape");
        }
        return outgoingCommands.get(symbol - TuringMachine.FIRST_CHAR);
    }

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