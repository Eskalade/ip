package nutrisoy.command;

import nutrisoy.exception.DukeException;
import nutrisoy.task.TaskList;
import nutrisoy.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui) throws DukeException {
        ui.showTaskList(tasks);
    }
}
