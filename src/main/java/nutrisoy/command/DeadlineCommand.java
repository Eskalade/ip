package nutrisoy.command;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import nutrisoy.task.*;
import nutrisoy.ui.Ui;
import nutrisoy.storage.Storage;
import nutrisoy.exception.DukeException;

public class DeadlineCommand extends Command {
    private final String arguments;

    public DeadlineCommand(String arguments) {
        this.arguments = arguments;
    }

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