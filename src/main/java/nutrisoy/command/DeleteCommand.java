package nutrisoy.command;

import nutrisoy.task.*;
import nutrisoy.ui.Ui;
import nutrisoy.storage.Storage;
import nutrisoy.exception.DukeException;

public class DeleteCommand extends Command {
    private final String indexString;

    public DeleteCommand(String indexString) {
        this.indexString = indexString;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
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