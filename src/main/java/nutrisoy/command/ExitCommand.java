package nutrisoy.command;

import nutrisoy.exception.DukeException;
import nutrisoy.task.TaskList;
import nutrisoy.ui.Ui;

/**
 * Represents a command that exits the application.
 */
public class ExitCommand extends Command {
    /**
     * Displays the application's farewell message.
     *
     * @param tasks current task list
     * @param ui user interface used to display the message
     * @param storage storage used by the application
     * @throws DukeException if the command cannot be executed
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws DukeException {
        ui.showGoodbye();
    }

    /**
     * Indicates that this command exits the application.
     *
     * @return {@code true}
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
