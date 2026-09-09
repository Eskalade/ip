package nutrisoy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import nutrisoy.task.Task;

public class StorageTest {
    @Test
    public void loadTasks_supportsLegacyAndTaggedRecords(@TempDir Path tempDirectory) throws IOException {
        Path storagePath = tempDirectory.resolve("tasks.txt");
        Files.writeString(storagePath, "T | 0 | legacy task\nT | 1 | tagged task | fun,school\n");

        ArrayList<Task> tasks = new Storage(storagePath.toString()).loadTasks();

        assertEquals(2, tasks.size());
        assertEquals("[T][ ] legacy task", tasks.get(0).toString());
        assertEquals("[T][X] tagged task #fun #school", tasks.get(1).toString());
        assertEquals("T | 1 | tagged task | fun,school", tasks.get(1).toFileFormat());
    }
}
