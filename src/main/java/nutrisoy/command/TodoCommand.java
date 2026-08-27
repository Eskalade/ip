package nutrisoy.command;

import nutrisoy.task.*;
import nutrisoy.ui.Ui;
import nutrisoy.storage.Storage;
import nutrisoy.exception.DukeException;

/**
 * Adds a todo task from a user-provided description.
 */
public class TodoCommand extends Command {
    private final String description;

    /**
     * Creates a command with the todo description.
     *
     * @param description description of the todo task
     */
    public TodoCommand(String description) {
        this.description = description;
    }

    /**
     * Adds the requested todo task and displays the result.
     *
     * @param tasks list to which the todo is added
     * @param ui user interface used to display the result
     * @param storage storage used by the application
     * @throws DukeException if the todo description is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (description.isEmpty()) {
            throw new DukeException("The description of a todo cannot be empty. Use: todo [description]");
        }
        Task newTodo = new Todo(description);
        tasks.add(newTodo);
        ui.showTaskAdded(newTodo, tasks.size());
    }
}
