import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EventCommand extends Command {
    private final String arguments;

    public EventCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (arguments.isEmpty()) {
            throw new DukeException("The description of an event cannot be empty. Use: event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");
        }
        int fromIndex = arguments.indexOf("/from");
        int toIndex = arguments.indexOf("/to");
        if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex) {
            throw new DukeException("An event requires both '/from' and '/to' parameters. Use: event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");
        }
        String description = arguments.substring(0, fromIndex).trim();
        String fromString = arguments.substring(fromIndex + 5, toIndex).trim();
        String toString = arguments.substring(toIndex + 3).trim();
        if (description.isEmpty()) {
            throw new DukeException("The description of an event cannot be empty.");
        }
        if (fromString.isEmpty() || toString.isEmpty()) {
            throw new DukeException("The start and end dates of an event cannot be empty.");
        }

        try {
            LocalDate fromDate = LocalDate.parse(fromString);
            LocalDate toDate = LocalDate.parse(toString);
            Task newEvent = new Event(description, fromDate, toDate);
            tasks.add(newEvent);
            ui.showTaskAdded(newEvent, tasks.size());
        } catch (DateTimeParseException e) {
            throw new DukeException("Please provide event dates in yyyy-MM-dd format (e.g., 2019-12-02).");
        }
    }
}