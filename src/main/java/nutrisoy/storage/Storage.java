package nutrisoy.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import nutrisoy.exception.DukeException;
import nutrisoy.task.Deadline;
import nutrisoy.task.Event;
import nutrisoy.task.Task;
import nutrisoy.task.Todo;

/**
 * Loads tasks from and saves tasks to a local file.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates storage that uses the specified file path.
     *
     * @param filePath path to the task data file
     */
    public Storage(String filePath) {
        assert filePath != null && !filePath.isBlank() : "Storage file path must be valid";
        this.filePath = filePath;
    }

    /**
     * Loads valid tasks from the storage file.
     *
     * @return tasks loaded from the storage file, or an empty list when none can be loaded
     * @throws DukeException if the configured path cannot be read
     */
    public ArrayList<Task> loadTasks() throws DukeException {
        assert filePath != null && !filePath.isBlank() : "Storage file path must be valid";
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }
        if (!file.isFile() || !file.canRead()) {
            throw new DukeException("The storage path is not a readable file: " + filePath);
        }

        try (Scanner scanner = new Scanner(file)) {
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    Task task = parseLineToTask(line);
                    if (tasks.stream().anyMatch(existingTask -> existingTask.hasSameDetails(task))) {
                        throw new DukeException("Duplicate task");
                    }
                    tasks.add(task);
                } catch (Exception e) {
                    System.out.println(" Warning: Skipping invalid data on line " + lineNumber + ".");
                }
            }
        } catch (IOException e) {
            throw new DukeException("I couldn't read the task file. Check that it is accessible.");
        }

        return tasks;
    }

    /**
     * Saves the supplied tasks to the storage file.
     *
     * @param tasks tasks to persist
     * @throws DukeException if the tasks cannot be written to the configured path
     */
    public void saveTasks(ArrayList<Task> tasks) throws DukeException {
        assert tasks != null : "Tasks to save must not be null";
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
                throw new DukeException("I couldn't create the data directory needed to save your tasks.");
            }
            if (parentDir != null && !parentDir.isDirectory()) {
                throw new DukeException("The configured data location is not a directory.");
            }

            try (FileWriter writer = new FileWriter(file)) {
                for (Task task : tasks) {
                    writer.write(task.toFileFormat() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new DukeException("I couldn't save your tasks. Check the data file permissions and try again.");
        }
    }

    private Task parseLineToTask(String line) throws DukeException {
        assert line != null : "Storage line must not be null";
        String[] parts = line.split(" \\| ", -1);
        if (parts.length < 3) {
            throw new DukeException("Corrupted format");
        }
        assert parts.length >= 3 : "A task line must contain type, status, and description";

        String type = parts[0];
        if (!parts[1].equals("0") && !parts[1].equals("1")) {
            throw new DukeException("Invalid completion status");
        }
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        if (description.isBlank()) {
            throw new DukeException("Missing task description");
        }

        Task task;
        int tagFieldIndex;
        switch (type) {
            case "T":
                if (parts.length > 4) {
                    throw new DukeException("Unexpected todo fields");
                }
                task = new Todo(description);
                tagFieldIndex = 3;
                break;
            case "D":
                if (parts.length < 4) {
                    throw new DukeException("Missing deadline date");
                }
                if (parts.length > 5) {
                    throw new DukeException("Unexpected deadline fields");
                }
                assert parts.length >= 4 : "A deadline line must contain a date";
                LocalDate byDate;
                try {
                    byDate = LocalDate.parse(parts[3]);
                    tagFieldIndex = 4;
                } catch (DateTimeParseException e) {
                    if (parts.length < 5) {
                        throw new DukeException("Invalid deadline date");
                    }
                    byDate = LocalDate.parse(parts[4]);
                    tagFieldIndex = 3;
                }
                task = new Deadline(description, byDate);
                break;
            case "E":
                if (parts.length < 5) {
                    throw new DukeException("Missing event timeline");
                }
                if (parts.length > 6) {
                    throw new DukeException("Unexpected event fields");
                }
                assert parts.length >= 5 : "An event line must contain two dates";
                LocalDate fromDate;
                LocalDate toDate;
                try {
                    fromDate = LocalDate.parse(parts[3]);
                    toDate = LocalDate.parse(parts[4]);
                    tagFieldIndex = 5;
                } catch (DateTimeParseException e) {
                    if (parts.length < 6) {
                        throw new DukeException("Invalid event timeline");
                    }
                    fromDate = LocalDate.parse(parts[4]);
                    toDate = LocalDate.parse(parts[5]);
                    tagFieldIndex = 3;
                }
                if (!fromDate.isBefore(toDate)) {
                    throw new DukeException("Event start date must precede its end date");
                }
                task = new Event(description, fromDate, toDate);
                break;
            default:
                throw new DukeException("Unknown task type");
        }

        if (parts.length > tagFieldIndex) {
            addTags(task, parts[tagFieldIndex]);
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    private void addTags(Task task, String tagsField) {
        if (tagsField.isEmpty()) {
            return;
        }
        for (String tag : tagsField.split(",", -1)) {
            if (!Task.isValidTagName(tag)) {
                throw new IllegalArgumentException("Invalid stored tag");
            }
            if (task.hasTag(tag)) {
                throw new IllegalArgumentException("Duplicate stored tag");
            }
            task.addTag(tag);
        }
    }
}
