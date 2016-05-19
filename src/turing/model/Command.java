package turing.model;

public class Command implements Comparable<Command> {
    private State source;
    private State target;
    private char inputChar;
    private Direction inputHeadDirection;
    private char[] commandChars;
    private char[] newChars;
    private Direction[] headDirections;

    public Command(State source, State target, char inputChar, Direction inputHeadDirection,
            char[] commandChars, char[] newChars, Direction[] headDirections) {
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

    @Override
    public int compareTo(Command otherCommand) {
        if (this.target.getNumberOfState() < otherCommand.source
                .getNumberOfState()) {
            return -1;
        }
        if (this.target.getNumberOfState() > otherCommand.source
                .getNumberOfState()) {
            return 1;
        }

        for (int i = 0; i < commandChars.length; i++) {
            if (this.commandChars[i] < otherCommand.commandChars[i]) {
                return -1;
            }
            if (this.commandChars[i] > otherCommand.commandChars[i]) {
                return 1;
            }
            if (this.headDirections[i].compareTo(otherCommand.headDirections[i]) < 0) {
                return -1;
            }
            if (this.headDirections[i].compareTo(otherCommand.headDirections[i]) > 0) {
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
        transitionString.append(" ");
        transitionString.append(inputChar);
        for (char c : commandChars) {
            transitionString.append(" ");
            transitionString.append(c);
        }
        for (int i = 0; i < this.newChars.length; i++) {
            transitionString.append(" ");
            transitionString.append(this.commandChars[i]);
            transitionString.append(" ");
            transitionString.append(this.headDirections[i]);
        }
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
                    && this.commandChars.equals(otherCommand.commandChars));
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