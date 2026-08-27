package nutrisoy.command;

import nutrisoy.exception.DukeException;
import nutrisoy.task.Task;
import nutrisoy.task.TaskList;
import nutrisoy.ui.Ui;

/**
 * Marks a task identified by a user-provided index as complete.
 */
public class MarkCommand extends Command {
    private final String indexString;

    /**
     * Creates a command with the task index to mark.
     *
     * @param indexString one-based index supplied by the user
     */
    public MarkCommand(String indexString) {
        this.indexString = indexString;
    }

    /**
     * Marks the requested task as complete and displays the result.
     *
     * @param tasks list containing the task to mark
     * @param ui user interface used to display the result
     * @param storage storage used by the application
     * @throws DukeException if the task index is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws DukeException {
        if (indexString.isEmpty()) {
            throw new DukeException("Please specify the task number to mark as done. Use: mark [index]");
        }
        try {
            int index = Integer.parseInt(indexString) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new DukeException("Task number out of range. You currently have " + tasks.size() + " tasks.");
            }
            Task task = tasks.get(index);
            task.markAsDone();
            ui.showTaskMarked(task);
        } catch (NumberFormatException e) {
            throw new DukeException("The task number must be a valid integer.");
        }
    }
}
