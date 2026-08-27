package nutrisoy;

import nutrisoy.ui.Ui;
import nutrisoy.storage.Storage;
import nutrisoy.task.TaskList;
import nutrisoy.command.Command;
import nutrisoy.parser.Parser;
import nutrisoy.exception.DukeException;

/**
 * Coordinates the NutriSoy application's user interface, storage, and tasks.
 */
public class Duke {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Creates the application and loads tasks from the specified storage file.
     *
     * @param filePath path to the file used to persist tasks
     */
    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the command-processing loop until the user exits the application.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
                storage.saveTasks(tasks.getTasks()); // Auto-save state to storage
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Starts the NutriSoy application.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        new Duke("./data/duke.txt").run();
    }
}
