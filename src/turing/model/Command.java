package turing.model;

public class Command implements Comparable<Command> {
    private State source;
    private State target;
    private char[] commandChars;
    private Direction[] directions;

    public Command(State source, State target, char[] commandChars,
            Direction[] directions) {
        if (commandChars.length != directions.length) {
            throw new IllegalArgumentException("The number of characters must"
                    + "match the number of directions in a command!");
        }
        this.source = source;
        this.target = target;
        this.commandChars = commandChars;
        this.directions = directions;
    }

    State getTarget() {
        return this.target;
    }

    char[] getCommandChars() {
        return this.commandChars;
    }

    Direction[] getDirections() {
        return this.directions;
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
            if (this.directions[i]
                    .compareTo(otherCommand.directions[i]) < 0) {
                return -1;
            }
            if (this.directions[i]
                    .compareTo(otherCommand.directions[i]) > 0) {
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
        for (int i = 0; i < this.commandChars.length; i++) {
            transitionString.append(" ");
            transitionString.append(this.commandChars[i]);
            transitionString.append(" ");
            transitionString.append(this.directions[i]);
        }
        return transitionString.toString();
    }
    
    @Override 
    public boolean equals (Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof Command) {
            Command otherCommand = (Command) other;
            return (this.source == otherCommand.source && this.target == otherCommand.target && this.commandChars.equals(otherCommand.commandChars) && this.directions.equals(otherCommand.directions));
            } 
        else { 
            return false;
        }
    }
    
    @Override
    public int hashCode () {
        StringBuilder b = new StringBuilder(this.source.toString());
        for (char c : this.commandChars) {
            b.append("," + c);
        }
        return b.toString().hashCode();
   }
}