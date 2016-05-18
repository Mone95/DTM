package turing.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class State {
    private final int numberOfState;
    private final boolean isStoppingState;
    private final boolean isAcceptingState;
    private List<List<Transition>> outgoingTransitions =
            new ArrayList<List<Transition>>(DTM.ALPHABET_LENGTH);

    public State(int numberOfState, boolean isStoppingState,
            boolean isAcceptingState) {
        this.numberOfState = numberOfState;
        this.isStoppingState = isStoppingState;
        this.isAcceptingState = isAcceptingState;
        for (int i = 0; i < DTM.ALPHABET_LENGTH; i++) {
            outgoingTransitions.add(new ArrayList<Transition>());
        }
    }

    int getNumberOfState() {
        return this.numberOfState;
    }

    boolean isStoppingState() {
        return this.isStoppingState;
    }

    boolean isAcceptingState() {
        return this.isAcceptingState;
    }

    private List<Transition> getTransitionsOfInputSymbol(char symbol) {
        if (symbol < TuringMachine.FIRST_CHAR
                || symbol > TuringMachine.LAST_CHAR) {
            throw new IllegalArgumentException(
                    "A symbol that is not in the Alphabet has been given to "
                            + "the Input Tape");
        }
        return outgoingTransitions.get(symbol - TuringMachine.FIRST_CHAR);
    }

    @Override
    public String toString() {
        for (List<Transition> transitions : this.outgoingTransitions) {
            Collections.sort(transitions);
        }
        StringBuilder stateAsString = new StringBuilder();
        for (List<Transition> transitions : outgoingTransitions) {
            for (Transition transition : transitions) {
                stateAsString.append(transition);
                stateAsString.append("\n");
            }
        }
        return stateAsString.toString();
    }
}