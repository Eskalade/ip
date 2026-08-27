package nutrisoy.command;

import nutrisoy.task.TaskList;
import nutrisoy.ui.Ui;
import nutrisoy.storage.Storage;
import nutrisoy.exception.DukeException;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException;
    
    public boolean isExit() {
        return false;
    }
}