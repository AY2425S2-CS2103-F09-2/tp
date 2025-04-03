package seedu.address.model;

import java.util.ArrayList;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * StateList class that contains the states of TaskLists
 */
public class AddressBookStateManager {
    private static final ArrayList<AddressBook> states = new ArrayList<>();
    private static int currentState = 0;
    private static Command previousCommand = null;

    /**
     * Instantiate task list with old task list data loaded in.
     */
    public AddressBookStateManager() {
    }

    /**
     * Add a new state and move current state number forward by one.
     */
    public static void addState(AddressBook state) {
        while (states.size() > currentState) {
            states.remove(states.size() - 1);
        }

        AddressBookStateManager.states.add(state);
        AddressBookStateManager.currentState++;
    }

    /**
     * Move current state number back by one
     */
    public static void undo() throws CommandException {
        if (currentState > 1) {
            currentState--;
        } else {
            throw new CommandException("No previous model state found");
        }
    }

    /**
     * Get the current state.
     */
    public static AddressBook getCurrentState() {
        return states.get(currentState - 1);
    }

    /**
     * Undoes an Undo command by moving to the state before an Undo command.
     */
    public static void redo() throws CommandException {
        if (currentState < states.size()) {
            currentState++;
        } else {
            throw new CommandException("No state to redo");
        }
    }

    /**
     * Remove all states after the current state.
     */
    public static void clearFutureStates() {
        while (states.size() > currentState) {
            states.remove(states.size() - 1);
        }
    }

    /**
     * Set the previous command.
     */
    public static void setPreviousCommand(Command command) {
        previousCommand = command;
    }
}
