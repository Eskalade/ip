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
        assert indexString != null : "Task index must not be null";
        this.indexString = indexString;
    }

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, Ui ui) throws DukeException {
        assert tasks != null && ui != null : "Command collaborators must not be null";
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
