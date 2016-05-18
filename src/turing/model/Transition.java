package turing.model;

public class Transition implements Comparable<Transition> {
    private State source;
    private State target;
    private char[] transitionChars;
    private Direction[] directions;

    public Transition(State source, State target, char[] transitionChars,
            Direction[] directions) {
        if (transitionChars.length != directions.length) {
            throw new IllegalArgumentException("The number of characters must"
                    + "match the number of directions in a transition!");
        }
        this.source = source;
        this.target = target;
        this.transitionChars = transitionChars;
        this.directions = directions;
    }

    State getTarget() {
        return this.target;
    }

    char[] getTransitionChars() {
        return this.transitionChars;
    }

    Direction[] getDirections() {
        return this.directions;
    }

    @Override
    public int compareTo(Transition otherTransition) {
        if (this.source.getNumberOfState() < otherTransition.source
                .getNumberOfState()) {
            return -1;
        }
        if (this.source.getNumberOfState() > otherTransition.source
                .getNumberOfState()) {
            return 1;
        }
        if (this.target.getNumberOfState() < otherTransition.source
                .getNumberOfState()) {
            return -1;
        }
        if (this.target.getNumberOfState() > otherTransition.source
                .getNumberOfState()) {
            return 1;
        }

        for (int i = 0; i < transitionChars.length; i++) {
            if (this.transitionChars[i] < otherTransition.transitionChars[i]) {
                return -1;
            }
            if (this.transitionChars[i] > otherTransition.transitionChars[i]) {
                return 1;
            }
            if (this.directions[i]
                    .compareTo(otherTransition.directions[i]) < 0) {
                return -1;
            }
            if (this.directions[i]
                    .compareTo(otherTransition.directions[i]) > 0) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder transitionString = new StringBuilder();
        transitionString.append(this.source.getNumberOfState());
        transitionString.append(" ");
        transitionString.append(this.target.getNumberOfState());
        for (int i = 0; i < this.transitionChars.length; i++) {
            transitionString.append(" ");
            transitionString.append(this.transitionChars[i]);
            transitionString.append(" ");
            transitionString.append(this.directions[i]);
        }
        return transitionString.toString();
    }
}