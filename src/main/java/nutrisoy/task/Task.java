package nutrisoy.task;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    private final LinkedHashSet<String> tags;

    /**
     * Creates an incomplete task with the supplied description.
     *
     * @param description description of the task
     */
    public Task(String description) {
        assert description != null : "Task description must not be null";
        this.description = description;
        this.isDone = false;
        this.tags = new LinkedHashSet<>();
    }

    /**
     * Returns the icon representing this task's completion status.
     *
     * @return {@code "X"} if complete; otherwise a space character
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }


    /**
     * Marks this task as complete.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as incomplete.
     */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Adds a unique tag to this task.
     *
     * @param tagName name of the tag to add
     */
    public void addTag(String tagName) {
        assert tagName != null && !tagName.isBlank() : "Tag name must be valid";
        tags.add(tagName);
    }

    /**
     * Removes a tag from this task if it exists.
     *
     * @param tagName name of the tag to remove
     */
    public void removeTag(String tagName) {
        assert tagName != null && !tagName.isBlank() : "Tag name must be valid";
        tags.remove(tagName);
    }

    /**
     * Returns this task in the storage-file format.
     *
     * @return storage-file representation of this task
     */
    public String toFileFormat() {
        return (isDone ? "1" : "0") + " | " + description + " | " + String.join(",", tags);
    }

    /**
     * Returns a display representation of this task.
     *
     * @return display representation of this task
     */
    @Override
    public String toString() {
        String formattedTags = tags.stream()
                .map(tag -> "#" + tag)
                .collect(Collectors.joining(" "));
        String tagSuffix = formattedTags.isEmpty() ? "" : " " + formattedTags;
        return "[" + getStatusIcon() + "] " + description + tagSuffix;
    }
}
