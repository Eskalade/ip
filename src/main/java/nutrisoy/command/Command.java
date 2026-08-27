package nutrisoy.command;

import nutrisoy.exception.DukeException;
import nutrisoy.task.TaskList;
import nutrisoy.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui) throws DukeException;

    public boolean isExit() {
        return false;
    }
}
