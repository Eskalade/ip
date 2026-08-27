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
     * Indicates whether this command ends the application.
     *
     * @return {@code true} if this command requests application exit; {@code false} otherwise
     */
    public boolean isExit() {
        return false;
    }
}
