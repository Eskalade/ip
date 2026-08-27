package nutrisoy.command;

import nutrisoy.exception.DukeException;
import nutrisoy.task.Task;
import nutrisoy.task.TaskList;
import nutrisoy.ui.Ui;

/**
 * Removes a task identified by a user-provided index.
 */
public class DeleteCommand extends Command {
    private final String indexString;

    /**
     * Creates a command with the task index to remove.
     *
     * @param indexString one-based index supplied by the user
     */
    public DeleteCommand(String indexString) {
        this.indexString = indexString;
    }

    /**
     * Removes the requested task and displays the result.
     *
     * @param tasks list from which the task is removed
     * @param ui user interface used to display the result
     * @param storage storage used by the application
     * @throws DukeException if the task index is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws DukeException {
        if (indexString.isEmpty()) {
            throw new DukeException("Please specify the task number to delete. Use: delete [index]");
        }
        try {
            int index = Integer.parseInt(indexString) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new DukeException("Task number out of range. You currently have " + tasks.size() + " tasks.");
            }
            Task removedTask = tasks.remove(index);
            ui.showTaskRemoved(removedTask, tasks.size());
        } catch (NumberFormatException e) {
            throw new DukeException("The task number must be a valid integer.");
        }
    }
}
