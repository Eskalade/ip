package nutrisoy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import nutrisoy.ui.GuiResponse;

/** Integrates command execution, error metadata, console interaction, and persistence. */
public class DukeTest {
    @TempDir
    private Path directory;

    @Test
    public void responses_restart_restoresTypesTagsAndCompletion() throws Exception {
        Path file = directory.resolve("tasks.txt");
        Duke duke = new Duke(file.toString());
        assertFalse(duke.getGuiResponse("todo read").isError());
        assertFalse(duke.getGuiResponse("deadline report /by 2028-02-29").isError());
        assertFalse(duke.getGuiResponse("event trip /from 2026-12-31 /to 2027-01-01").isError());
        assertFalse(duke.getGuiResponse("tag 2 work").isError());
        assertFalse(duke.getGuiResponse("mark 2").isError());

        Duke restarted = new Duke(file.toString());
        String response = restarted.getResponse("list");
        assertTrue(response.contains("1.[T][ ] read"));
        assertTrue(response.contains("2.[D][X] report #work"));
        assertTrue(response.contains("3.[E][ ] trip"));
        assertFalse(restarted.getGuiResponse("untag 2 WORK").isError());
        assertFalse(restarted.getGuiResponse("unmark 2").isError());
        assertFalse(restarted.getGuiResponse("delete 1").isError());
        assertEquals(List.of("D | 0 | report | 2028-02-29",
                "E | 0 | trip | 2026-12-31 | 2027-01-01"), Files.readAllLines(file));
        assertFalse(new Duke(file.toString()).getResponse("list").contains("read"));
    }

    @Test
    public void response_invalidThenValid_resetsErrorFlagAndCapturedText() throws Exception {
        Path file = directory.resolve("tasks.txt");
        Duke duke = new Duke(file.toString());
        duke.getResponse("todo read");
        String saved = Files.readString(file);
        for (String input : List.of("nonsense", "delete 9", "deadline bad /by 2026-02-30")) {
            GuiResponse error = duke.getGuiResponse(input);
            assertTrue(error.isError(), input);
            assertTrue(error.getMessage().contains("Nice try."));
            assertEquals(saved, Files.readString(file));
        }
        GuiResponse success = duke.getGuiResponse("list");
        assertFalse(success.isError());
        assertTrue(success.getMessage().contains("read"));
        assertFalse(success.getMessage().contains("Nice try."));
    }

    @Test
    public void response_saveFailure_reportsErrorAndCanRetry() throws Exception {
        Path parent = directory.resolve("blocked");
        Files.writeString(parent, "blocking file");
        Duke duke = new Duke(parent.resolve("tasks.txt").toString());
        GuiResponse error = duke.getGuiResponse("todo read");
        assertTrue(error.isError());
        assertTrue(error.getMessage().contains("not a directory"));
        assertEquals("blocking file", Files.readString(parent));
        Files.move(parent, directory.resolve("original-blocker.txt"));
        GuiResponse retry = duke.getGuiResponse("list");
        assertFalse(retry.isError());
        assertEquals(List.of("T | 0 | read"), Files.readAllLines(parent.resolve("tasks.txt")));
    }

    @Test
    public void constructor_unreadablePath_reportsLoadFailure() {
        PrintStream original = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (PrintStream capture = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            Duke duke = new Duke(directory.toString());
            assertTrue(duke.getGuiResponse("list").isError());
            assertTrue(output.toString(StandardCharsets.UTF_8).contains("not a readable file"));
        } finally {
            System.setOut(original);
        }
    }

    @Test
    public void run_invalidThenValidCommands_continuesAndSavesBeforeExit() throws Exception {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Path file = directory.resolve("console.txt");
        String script = "nonsense\ntodo read\nmark 1\nlist\nbye\n";
        try (PrintStream capture = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setIn(new ByteArrayInputStream(script.getBytes(StandardCharsets.UTF_8)));
            System.setOut(capture);
            new Duke(file.toString()).run();
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
        String transcript = output.toString(StandardCharsets.UTF_8);
        assertTrue(transcript.contains("Soya"));
        assertTrue(transcript.contains("Nice try."));
        assertTrue(transcript.contains("1.[T][X] read"));
        assertTrue(transcript.contains("That's a wrap"));
        assertEquals(List.of("T | 1 | read"), Files.readAllLines(file));
    }
}
