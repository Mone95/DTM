package turing.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class State {
    private final int numberOfState;
    private final boolean isStoppingState;
    private final boolean isAcceptingState;
    private Collection<List<Transition>> outgoingTransitions = new ArrayList<List<Transition>>(DTM.ALPHABET_LENGTH);
    
    public State (int numberOfState, boolean isStoppingState, boolean isAcceptingState) {
        this.numberOfState = numberOfState;
        this.isStoppingState = isStoppingState;
        this.isAcceptingState = isAcceptingState;
        for (int i = 0; i < DTM.ALPHABET_LENGTH; i++) {
             outgoingTransitions.add(new ArrayList<Transition>());
        }
    }
    
    public int getNumberOfState () {
        return this.numberOfState;
    }
}