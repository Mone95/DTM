package turing.model;

import java.util.Arrays;

/**
 * A data bean for the commands of the Turing Machine.
 */
public class Command implements Comparable<Command> {
    private State source;
    private State target;
    private char inputChar;
    private Direction inputHeadDirection;
    private char[] commandChars;
    private char[] newChars;
    private Direction[] headDirections;

    /**
     * Creates a new command with source {@code source}, target {@code target},
     * input tape char {@code inputChar}, direction to move the input tape 
     * {@code inputHeadDirection}, current chars on the working tapes 
     * {@code commandChars}, letters to be written on the working tapes 
     * {@code newChars} and directions to move the heads of the working tapes
     * {@code headDirections}.
     * 
     * @param source
     * @param target
     * @param inputChar
     * @param inputHeadDirection
     * @param commandChars
     * @param newChars
     * @param headDirections
     */
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

    /**
     * Creates a new command dummy for the configuration determined by the 
     * arguments without any transitions.

     * @param source The source of the command.
     * @param inputChar The letter on the input tape.
     * @param commandChars The letters on the working tapes.
     */
    private Command(State source, char inputChar, char[] commandChars) {
        this.source = source;
        this.inputChar = inputChar;
        this.commandChars = commandChars;
    }
    
    /**
     * Returns the target of the command.
     * 
     * @return the target of the command.
     */
    State getTarget() {
        return this.target;
    }

    /**
     * Returns the direction the input tape head has to be moved to.
     * 
     * @return the direction the input tape has to be moved to.
     */
    Direction getInputHeadDirection() {
        return this.inputHeadDirection;
    }

    /**
     * Returns the letters to be written to the working tapes.
     * 
     * @return the letters to be written to the working tapes.
     */
    char[] getNewChars() {
        return this.newChars;
    }

    /**
     * Returns the directions to move the heads of the working tapes to.
     * @return the directions to move the heads of the working tapes to.
     */
    Direction[] getHeadDirections() {
        return this.headDirections;
    }

    /**
     * Creates a command that only has a source state and knows the letters 
     * expected on the input and the working tapes. Used for searching lists of
     * commands and cannot be executed.
     * 
     * @param source The source state of the command.
     * @param inputTapeChar The letter currently on the input tape.
     * @param workingTapeChars The letters currently on the working tapes.
     * @return A command that can be used for searching for one command.
     */
    static Command getSearchDummy(State source, char inputTapeChar,
            char[] workingTapeChars) {
        return new Command(source, inputTapeChar, workingTapeChars);
    }

    /**
     * Compares two commands by lexicographical order, based on the following 
     * order of attributes: {@code inputChar}, {@code commandChars}, the number
     * of its target state, {@code inputHeadDirection}, {@code newChars} and {@code headDirections}. 
     * Returns {@code -1} if {@code this} is less than {@code otherCommand},
     * {@code 0} if they are equal and {@code 1} if {@code} this is greater than
     * {@code otherCommand}.
     * 
     * @param otherCommand The command this one is compared to.
     * @return whether the other command is less, equal to or greater than this.
     */
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

    /**
     * Returns a textual representation of the command in the form of:
     * (number of source state, input tape char, letter on the first working 
     * tape, letter on the second working tape, ...) -> (number of the target
     * state, direction to move the head on the input tape, letter to be written
     * on the first working tape, direction to move the head on the first
     * working tape, letter to be written on the second working tape, direction
     * to move the head on the second working tape, ...)
     * 
     * @return a String representation of the command.
     */
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

    /**
     * Returns whether a given object is equal to {@code this}. If {@code other}
     * is a command, this is ensured if at least the source states, the input
     * tape chars and the chars expected on the working tapes are the same.
     * Returns {@code true} if {@code this} is equal to {@code other} and 
     * {@code false} otherwise.
     * 
     * @return whether this and other a equal.
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        StringBuilder b = new StringBuilder(this.source.toString());
        for (char c : this.commandChars) {
            b.append("," + c);
        }
        return b.toString().hashCode();
    }
}