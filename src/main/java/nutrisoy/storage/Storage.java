package nutrisoy.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
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
        this.filePath = filePath;
    }

    /**
     * Loads valid tasks from the storage file.
     *
     * @return tasks loaded from the storage file, or an empty list when none can be loaded
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    Task task = parseLineToTask(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    System.out.println(" Warning: Skipping corrupted data line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println(" Warning: Error reading storage file. Starting with an empty list.");
        }

        return tasks;
    }

    /**
     * Saves the supplied tasks to the storage file.
     *
     * @param tasks tasks to persist
     */
    public void saveTasks(ArrayList<Task> tasks) {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (FileWriter writer = new FileWriter(file)) {
                for (Task task : tasks) {
                    writer.write(task.toFileFormat() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println(" Warning: Could not save tasks to disk.");
        }
    }

    private Task parseLineToTask(String line) throws DukeException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new DukeException("Corrupted format");
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length < 4) {
                    throw new DukeException("Missing deadline date");
                }
                LocalDate byDate = LocalDate.parse(parts[3]);
                task = new Deadline(description, byDate);
                break;
            case "E":
                if (parts.length < 5) {
                    throw new DukeException("Missing event timeline");
                }
                LocalDate fromDate = LocalDate.parse(parts[3]);
                LocalDate toDate = LocalDate.parse(parts[4]);
                task = new Event(description, fromDate, toDate);
                break;
            default:
                throw new DukeException("Unknown task type");
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
