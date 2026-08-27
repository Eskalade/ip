public class TodoCommand extends Command {
    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

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