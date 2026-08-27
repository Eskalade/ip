package nutrisoy.command;

import nutrisoy.exception.DukeException;
import nutrisoy.task.TaskList;
import nutrisoy.ui.Ui;

public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui) throws DukeException {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
