package nutrisoy.parser;

import nutrisoy.command.Command;
import nutrisoy.command.DeadlineCommand;
import nutrisoy.command.DeleteCommand;
import nutrisoy.command.EventCommand;
import nutrisoy.command.ExitCommand;
import nutrisoy.command.ListCommand;
import nutrisoy.command.MarkCommand;
import nutrisoy.command.TodoCommand;
import nutrisoy.command.UnmarkCommand;
import nutrisoy.exception.DukeException;

public class Parser {
    public static Command parse(String fullCommand) throws DukeException {
        String trimmed = fullCommand.trim();
        if (trimmed.isEmpty()) {
            throw new DukeException("Command cannot be empty.");
        }

        String[] parts = trimmed.split(" ", 2);
        String commandWord = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1].trim() : "";

        switch (commandWord) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "todo":
            return new TodoCommand(arguments);
        case "deadline":
            return new DeadlineCommand(arguments);
        case "event":
            return new EventCommand(arguments);
        case "mark":
            return new MarkCommand(arguments);
        case "unmark":
            return new UnmarkCommand(arguments);
        case "delete":
            return new DeleteCommand(arguments);
        default:
            throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }
}
