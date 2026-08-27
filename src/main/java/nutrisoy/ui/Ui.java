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
        System.out.println(divider);
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
        System.out.println(" OOPS!!! " + message);
    }

    /**
     * Displays an error message for an unsuccessful task load.
     */
    public void showLoadingError() {
        System.out.println(" OOPS!!! There was an error loading saved tasks. Starting with an empty list.");
    }

    /**
     * Displays the application's farewell message.
     */
    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
    }

    /**
     * Displays confirmation that a task was added.
     *
     * @param task task that was added
     * @param totalTasks number of tasks now in the list
     */
    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Displays confirmation that a task was removed.
     *
     * @param task task that was removed
     * @param totalTasks number of tasks now in the list
     */
    public void showTaskRemoved(Task task, int totalTasks) {
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Displays confirmation that a task was marked as complete.
     *
     * @param task task that was marked
     */
    public void showTaskMarked(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
    }

    /**
     * Displays confirmation that a task was marked as incomplete.
     *
     * @param task task that was unmarked
     */
    public void showTaskUnmarked(Task task) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
    }

    /**
     * Displays every task in the supplied task list.
     *
     * @param tasks task list to display
     */
    public void showTaskList(TaskList tasks) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
    }
}
