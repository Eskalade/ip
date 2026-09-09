package nutrisoy.command;

import nutrisoy.exception.DukeException;
import nutrisoy.task.TaskList;
import nutrisoy.ui.Ui;

/**
 * Represents an executable user command.
 */
public abstract class Command {
    /**
     * Executes this command using the application's current collaborators.
     *
     * @param tasks list of tasks to operate on
     * @param ui user interface used to display results
     * @throws DukeException if the command cannot be executed
     */
    public abstract void execute(TaskList tasks, Ui ui) throws DukeException;

    /**
     * Converts a user-provided one-based task index into a zero-based list index.
     *
     * @param indexString one-based task index supplied by the user
     * @param tasks list whose bounds should be checked
     * @param missingIndexMessage message to use when no index is supplied
     * @return zero-based task index
     * @throws DukeException if the index is missing, invalid, or out of range
     */
    protected int parseTaskIndex(String indexString, TaskList tasks, String missingIndexMessage)
            throws DukeException {
        if (indexString.isEmpty()) {
            throw new DukeException(missingIndexMessage);
        }
        try {
            int index = Integer.parseInt(indexString) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new DukeException("Task number out of range. You currently have " + tasks.size()
                        + " tasks.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new DukeException("The task number must be a valid integer.");
        }
    }

    /**
     * Parses the two arguments shared by tag and untag commands.
     *
     * @param arguments command arguments containing an index and tag name
     * @param commandName command name used in the usage message
     * @return the index and tag name arguments
     * @throws DukeException if the arguments are missing or malformed
     */
    protected static String[] parseTagArguments(String arguments, String commandName) throws DukeException {
        assert arguments != null && commandName != null : "Tag command arguments must not be null";
        if (arguments.isBlank()) {
            throw new DukeException("Usage: " + commandName + " [index] [tag]");
        }
        String[] parts = arguments.trim().split("\\s+");
        if (parts.length != 2) {
            throw new DukeException("Usage: " + commandName + " [index] [tag]");
        }
        return parts;
    }

    /**
     * Indicates whether this command ends the application.
     *
     * @return {@code true} if this command requests application exit; {@code false} otherwise
     */
    public boolean isExit() {
        return false;
    }
}
