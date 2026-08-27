package nutrisoy.command;

import nutrisoy.task.TaskList;
import nutrisoy.ui.Ui;
import nutrisoy.storage.Storage;
import nutrisoy.exception.DukeException;

/**
 * Represents an executable user command.
 */
public abstract class Command {
    /**
     * Executes this command using the application's current collaborators.
     *
     * @param tasks list of tasks to operate on
     * @param ui user interface used to display results
     * @param storage storage used by the application
     * @throws DukeException if the command cannot be executed
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException;
    
    /**
     * Indicates whether this command ends the application.
     *
     * @return {@code true} if this command requests application exit; {@code false} otherwise
     */
    public boolean isExit() {
        return false;
    }
}
