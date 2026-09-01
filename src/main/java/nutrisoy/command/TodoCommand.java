package nutrisoy.command;

import nutrisoy.exception.DukeException;
import nutrisoy.task.Task;
import nutrisoy.task.TaskList;
import nutrisoy.task.Todo;
import nutrisoy.ui.Ui;

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

    /** {@inheritDoc} */
    @Override
    public void execute(TaskList tasks, Ui ui) throws DukeException {
        if (description.isEmpty()) {
            throw new DukeException("The description of a todo cannot be empty. Use: todo [description]");
        }
        Task newTodo = new Todo(description);
        tasks.add(newTodo);
        ui.showTaskAdded(newTodo, tasks.size());
    }
}
