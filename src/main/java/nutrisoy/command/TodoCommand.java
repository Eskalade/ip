package nutrisoy.command;

import nutrisoy.exception.DukeException;
import nutrisoy.task.Task;
import nutrisoy.task.TaskList;
import nutrisoy.task.Todo;
import nutrisoy.ui.Ui;

public class TodoCommand extends Command {
    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

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
