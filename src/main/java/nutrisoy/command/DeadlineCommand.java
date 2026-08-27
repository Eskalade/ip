package nutrisoy.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import nutrisoy.task.*;
import nutrisoy.ui.Ui;
import nutrisoy.storage.Storage;
import nutrisoy.exception.DukeException;

/**
 * Adds a deadline task from user-provided command arguments.
 */
public class DeadlineCommand extends Command {
    private final String arguments;

    /**
     * Creates a command with the arguments describing the deadline.
     *
     * @param arguments deadline description and due-date information
     */
    public DeadlineCommand(String arguments) {
        this.arguments = arguments;
    }

    /**
     * Adds the requested deadline task and displays the result.
     *
     * @param tasks list to which the deadline is added
     * @param ui user interface used to display the result
     * @param storage storage used by the application
     * @throws DukeException if the deadline arguments are invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (arguments.isEmpty()) {
            throw new DukeException("The description of a deadline cannot be empty. Use: deadline [description] /by [yyyy-MM-dd]");
        }
        int byIndex = arguments.indexOf("/by");
        if (byIndex == -1) {
            throw new DukeException("A deadline requires a '/by' parameter. Use: deadline [description] /by [yyyy-MM-dd]");
        }
        String description = arguments.substring(0, byIndex).trim();
        String byString = arguments.substring(byIndex + 3).trim();
        if (description.isEmpty()) {
            throw new DukeException("The description of a deadline cannot be empty.");
        }
        if (byString.isEmpty()) {
            throw new DukeException("The deadline date cannot be empty.");
        }

        try {
            LocalDate byDate = LocalDate.parse(byString);
            Task newDeadline = new Deadline(description, byDate);
            tasks.add(newDeadline);
            ui.showTaskAdded(newDeadline, tasks.size());
        } catch (DateTimeParseException e) {
            throw new DukeException("Please provide the deadline date in yyyy-MM-dd format (e.g., 2019-12-02).");
        }
    }
}
