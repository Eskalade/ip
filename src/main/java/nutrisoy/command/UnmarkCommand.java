package nutrisoy.command;

import nutrisoy.exception.DukeException;
import nutrisoy.task.Task;
import nutrisoy.task.TaskList;
import nutrisoy.ui.Ui;

public class UnmarkCommand extends Command {
    private final String indexString;

    public UnmarkCommand(String indexString) {
        this.indexString = indexString;
    }

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
