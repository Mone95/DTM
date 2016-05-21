package turing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class State {
    private final int numberOfState;
    private final boolean isStopping;
    private final boolean isAccepting;
    private List<List<Command>> outgoingCommands =
            new ArrayList<List<Command>>(DTM.ALPHABET_LENGTH + 1);

    public State(int numberOfState, boolean isStoppingState,
            boolean isAcceptingState) {
        this.numberOfState = numberOfState;
        this.isStopping = isStoppingState;
        this.isAccepting = isAcceptingState;
        for (int i = 0; i < DTM.ALPHABET_LENGTH + 1; i++) {
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
    
    private static int getPositionInList(char c) {
        if ((c < TuringMachine.FIRST_CHAR || c > TuringMachine.LAST_CHAR) && c != TuringMachine.BLANK_CHAR) {
            throw new IllegalArgumentException("A symbol that is not in the alphabet has been used on a tape");
        }
        return c == DTM.BLANK_CHAR ? DTM.ALPHABET_LENGTH : c - DTM.FIRST_CHAR;
    }

    Command getCommandForCurrentConfiguration(char inputChar, char[] workingTapeChars) {
        List<Command> listOfCommands = outgoingCommands.get(getPositionInList(inputChar));
        int indexInList = listOfCommands.indexOf(Command.getSearchDummy(this, inputChar, workingTapeChars));
        return indexInList == -1 ? null : listOfCommands.get(indexInList);
    }
    
    void addCommand (State target, char inputChar, Direction inputHeadDirection, char[] commandChars, char[] newChars, Direction[] directions) {
        Command newCommand = new Command(this, target, inputChar, inputHeadDirection, commandChars, newChars, directions);
        List<Command> commandList = outgoingCommands.get(State.getPositionInList(inputChar));
        if (!commandList.contains(newCommand)) {
            commandList.add(newCommand);
        }
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