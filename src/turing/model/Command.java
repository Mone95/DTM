package turing.model;

import java.util.Arrays;

public class Command implements Comparable<Command> {
    private State source;
    private State target;
    private char inputChar;
    private Direction inputHeadDirection;
    private char[] commandChars;
    private char[] newChars;
    private Direction[] headDirections;

    public Command(State source, State target, char inputChar,
            Direction inputHeadDirection, char[] commandChars, char[] newChars,
            Direction[] headDirections) {
        if (newChars.length != headDirections.length) {
            throw new IllegalArgumentException("The number of characters must"
                    + "match the number of headDirections in a command!");
        }
        this.source = source;
        this.target = target;
        this.inputChar = inputChar;
        this.inputHeadDirection = inputHeadDirection;
        this.commandChars = commandChars;
        this.newChars = newChars;
        this.headDirections = headDirections;
    }

    private Command(State source, char inputChar, char[] commandChars) {
        this.source = source;
        this.inputChar = inputChar;
        this.commandChars = commandChars;
    }

    State getTarget() {
        return this.target;
    }

    Direction getInputHeadDirection() {
        return this.inputHeadDirection;
    }

    char[] getNewChars() {
        return this.newChars;
    }

    Direction[] getHeadDirections() {
        return this.headDirections;
    }

    static Command getSearchDummy(State source, char inputTapeChar,
            char[] workingTapeChars) {
        return new Command(source, inputTapeChar, workingTapeChars);
    }

    @Override
    public int compareTo(Command otherCommand) {
        if (this.inputChar < otherCommand.inputChar) {
            return -1;
        }
        if (this.inputChar > otherCommand.inputChar) {
            return 1;
        }
        for (int i = 0; i < this.commandChars.length; i++) {
            if (this.commandChars[i] < otherCommand.commandChars[i]) {
                return -1;
            }
            if (this.commandChars[i] > otherCommand.commandChars[i]) {
                return 1;
            }
        }
        if (this.target.getNumberOfState() < otherCommand.source
                .getNumberOfState()) {
            return -1;
        }
        if (this.target.getNumberOfState() > otherCommand.source
                .getNumberOfState()) {
            return 1;
        }
        if (this.inputHeadDirection != otherCommand.inputHeadDirection) {
            return this.inputHeadDirection
                    .compareDirections(otherCommand.inputHeadDirection);
        }
        for (int i = 0; i < this.newChars.length; i++) {
            if (this.newChars[i] < otherCommand.newChars[i]) {
                return -1;
            }
            if (this.newChars[i] > otherCommand.newChars[i]) {
                return 1;
            }
            if (this.headDirections[i] != otherCommand.headDirections[i]) {
                return this.headDirections[i]
                        .compareDirections(otherCommand.headDirections[i]);
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder transitionString = new StringBuilder();
        transitionString.append('(');
        transitionString.append(this.source.getNumberOfState());
        transitionString.append(", ");
        transitionString.append(inputChar);
        for (char c : commandChars) {
            transitionString.append(", ");
            transitionString.append(c);
        }
        transitionString.append(") -> (");
        transitionString.append(this.target.getNumberOfState());
        transitionString.append(", ");
        transitionString.append(this.inputHeadDirection);
        for (int i = 0; i < this.newChars.length; i++) {
            transitionString.append(", ");
            transitionString.append(this.newChars[i]);
            transitionString.append(", ");
            transitionString.append(this.headDirections[i]);
        }
        transitionString.append(')');
        return transitionString.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof Command) {
            Command otherCommand = (Command) other;
            return (this.source == otherCommand.source
                    && this.inputChar == otherCommand.inputChar
                    && Arrays.equals(this.commandChars,
                            otherCommand.commandChars));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        StringBuilder b = new StringBuilder(this.source.toString());
        for (char c : this.commandChars) {
            b.append("," + c);
        }
        return b.toString().hashCode();
    }
}