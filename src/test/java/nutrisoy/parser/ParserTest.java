package nutrisoy.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import nutrisoy.command.Command;
import nutrisoy.command.ExitCommand;
import nutrisoy.command.TagCommand;
import nutrisoy.command.TodoCommand;
import nutrisoy.command.UntagCommand;
import nutrisoy.exception.DukeException;

public class ParserTest {
    @Test
    public void parse_validTodoCommand_success() throws DukeException {
        Command cmd = Parser.parse("todo read book");
        assertTrue(cmd instanceof TodoCommand);
    }

    @Test
    public void parse_validExitCommand_success() throws DukeException {
        Command cmd = Parser.parse("bye");
        assertTrue(cmd instanceof ExitCommand);
    }

    @Test
    public void parse_validTagCommands_success() throws DukeException {
        assertTrue(Parser.parse("tag 1 fun") instanceof TagCommand);
        assertTrue(Parser.parse("untag 1 fun") instanceof UntagCommand);
    }

    @Test
    public void parse_invalidCommand_exceptionThrown() {
        assertThrows(DukeException.class, () -> {
            Parser.parse("invalidCommandWord");
        });
    }

    @Test
    public void parse_emptyInput_exceptionThrown() {
        assertThrows(DukeException.class, () -> {
            Parser.parse("   ");
        });
    }

    @Test
    public void parse_malformedTagCommand_exceptionThrown() {
        assertThrows(DukeException.class, () -> Parser.parse("tag 1"));
        assertThrows(DukeException.class, () -> Parser.parse("untag 1 fun extra"));
    }
}
