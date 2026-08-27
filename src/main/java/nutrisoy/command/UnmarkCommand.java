package nutrisoy.command;

import nutrisoy.exception.DukeException;
import nutrisoy.task.Task;
import nutrisoy.task.TaskList;
import nutrisoy.ui.Ui;

/**
 * Marks a task identified by a user-provided index as incomplete.
 */
public class UnmarkCommand extends Command {
    private final String indexString;

    /**
     * Creates a command with the task index to unmark.
     *
     * @param indexString one-based index supplied by the user
     */
    public UnmarkCommand(String indexString) {
        this.indexString = indexString;
    }

    /**
     * Marks the requested task as incomplete and displays the result.
     *
     * @param tasks list containing the task to unmark
     * @param ui user interface used to display the result
     * @param storage storage used by the application
     * @throws DukeException if the task index is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws DukeException {
        if (indexString.isEmpty()) {
            throw new DukeException("Please specify the task number to unmark. Use: unmark [index]");
        }
        try {
            int index = Integer.parseInt(indexString) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new DukeException("Task number out of range. You currently have " + tasks.size() + " tasks.");
            }
            Task task = tasks.get(index);
            task.unmarkAsDone();
            ui.showTaskUnmarked(task);
        } catch (NumberFormatException e) {
            throw new DukeException("The task number must be a valid integer.");
        }
    }
}
