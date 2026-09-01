package nutrisoy.ui;

import java.util.Scanner;

import nutrisoy.task.Task;
import nutrisoy.task.TaskList;

/**
 * Handles console input and output for the NutriSoy application.
 */
public class Ui {
    private final String divider = "____________________________________________________________";
    private final Scanner scanner;
    private StringBuilder capturedOutput;

    /**
     * Creates a user interface that reads commands from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the application's welcome message.
     */
    public void showWelcome() {
        String logo = """
                _   __      __         _  _____
               / | / /_  __/ /_________(_)/ ___/____  __  __
              /  |/ / / / / __/ ___/  _  /\\__ \\/ __ \\/ / / /
             / /|  / /_/ / /_/ /   / / / /___/ / /_/ / /_/ /
            /_/ |_/\\__,_/\\__/_/   /_/ /_//____/\\____/\\__, /
                                                    /____/
                """;
        System.out.println(divider);
        System.out.print(logo);
        System.out.println(" Hello! I'm NutriSoy");
        System.out.println(" What can I do for you?");
        System.out.println(divider);
    }

    /**
     * Displays a divider line.
     */
    public void showLine() {
        showMessage(divider);
    }

    /**
     * Reads the next command entered by the user.
     *
     * @return command read from standard input
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays an error message.
     *
     * @param message error explanation to display
     */
    public void showError(String message) {
        showMessage(" OOPS!!! " + message);
    }

    /**
     * Displays an error message for an unsuccessful task load.
     */
    public void showLoadingError() {
        showMessage(" OOPS!!! There was an error loading saved tasks. Starting with an empty list.");
    }

    /**
     * Displays the application's farewell message.
     */
    public void showGoodbye() {
        showMessage(" Bye. Hope to see you again soon!");
    }

    /**
     * Displays confirmation that a task was added.
     *
     * @param task task that was added
     * @param totalTasks number of tasks now in the list
     */
    public void showTaskAdded(Task task, int totalTasks) {
        showMessage(" Got it. I've added this task:");
        showMessage("   " + task);
        showMessage(" Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Displays confirmation that a task was removed.
     *
     * @param task task that was removed
     * @param totalTasks number of tasks now in the list
     */
    public void showTaskRemoved(Task task, int totalTasks) {
        showMessage(" Noted. I've removed this task:");
        showMessage("   " + task);
        showMessage(" Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Displays confirmation that a task was marked as complete.
     *
     * @param task task that was marked
     */
    public void showTaskMarked(Task task) {
        showMessage(" Nice! I've marked this task as done:");
        showMessage("   " + task);
    }

    /**
     * Displays confirmation that a task was marked as incomplete.
     *
     * @param task task that was unmarked
     */
    public void showTaskUnmarked(Task task) {
        showMessage(" OK, I've marked this task as not done yet:");
        showMessage("   " + task);
    }

    /**
     * Displays every task in the supplied task list.
     *
     * @param tasks task list to display
     */
    public void showTaskList(TaskList tasks) {
        showMessage(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            showMessage(" " + (i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays the list of tasks that match the search keyword.
     *
     * @param matchingTasks The TaskList containing the matching tasks.
     */
    public void showMatchingTasks(TaskList matchingTasks) {
        if (matchingTasks.isEmpty()) {
            showMessage(" No matching tasks found in your list.");
            return;
        }
        showMessage(" Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            showMessage(" " + (i + 1) + "." + matchingTasks.get(i));
        }
    }

    /**
     * Begins collecting messages for a graphical user interface response.
     */
    public void startCapturingOutput() {
        capturedOutput = new StringBuilder();
    }

    /**
     * Stops collecting messages and returns the accumulated response.
     *
     * @return response produced since output capture began
     */
    public String stopCapturingOutput() {
        String response = capturedOutput.toString().stripTrailing();
        capturedOutput = null;
        return response;
    }

    /**
     * Displays a message in the console or appends it to a GUI response.
     *
     * @param message text to display or capture
     */
    private void showMessage(String message) {
        if (capturedOutput == null) {
            System.out.println(message);
            return;
        }
        capturedOutput.append(message).append(System.lineSeparator());
    }
}
