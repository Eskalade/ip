package nutrisoy.command;

import nutrisoy.task.TaskList;
import nutrisoy.ui.Ui;
import nutrisoy.storage.Storage;
import nutrisoy.exception.DukeException;

/**
 * Displays all tasks in the task list.
 */
public class ListCommand extends Command {
    /**
     * Displays the current task list.
     *
     * @param tasks task list to display
     * @param ui user interface used to display the list
     * @param storage storage used by the application
     * @throws DukeException if the command cannot be executed
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        ui.showTaskList(tasks);
    }
}
