import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        String logo = """
                _   __      __         _  _____              
               / | / /_  __/ /_________(_)/ ___/____  __  __ 
              /  |/ / / / / __/ ___/  _  /\\__ \\/ __ \\/ / / / 
             / /|  / /_/ / /_/ /   / / / /___/ / /_/ / /_/ /  
            /_/ |_/\\__,_/\\__/_/   /_/ /_//____/\\____/\\__, /   
                                                    /____/    
            """;

        String divider = "____________________________________________________________";

        // Greet
        System.out.println(divider);
        System.out.print(logo);
        System.out.println(" Hello! I'm NutriSoy");
        System.out.println(" What can I do for you?");
        System.out.println(divider);

        // Initialize storage and load existing tasks
        Storage storage = new Storage("./data/duke.txt");
        ArrayList<Task> tasks = storage.loadTasks();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }

            String[] parts = input.split(" ", 2);
            Command cmd = Command.fromString(parts[0]);
            String arguments = parts.length > 1 ? parts[1].trim() : "";

            if (cmd == Command.BYE) {
                break;
            }

            System.out.println(divider);

            try {
                processCommand(cmd, arguments, tasks);
                storage.saveTasks(tasks); // Saves state automatically on success
            } catch (DukeException e) {
                System.out.println(" OOPS!!! " + e.getMessage());
            }

            System.out.println(divider);
        }

        // Exit
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(divider);

        scanner.close();
    }

    private static void processCommand(Command cmd, String arguments, ArrayList<Task> tasks) throws DukeException {
        switch (cmd) {
        case LIST:
            if (tasks.isEmpty()) {
                System.out.println(" Your list is currently empty.");
                return;
            }
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
            break;

        case TODO:
            if (arguments.isEmpty()) {
                throw new DukeException("The description of a todo cannot be empty. Use: todo [description]");
            }
            Task newTodo = new Todo(arguments);
            tasks.add(newTodo);
            printAddedConfirmation(newTodo, tasks.size());
            break;

        case DEADLINE:
            if (arguments.isEmpty()) {
                throw new DukeException("The description of a deadline cannot be empty. Use: deadline [description] /by [yyyy-MM-dd]");
            }
            int byIndex = arguments.indexOf("/by");
            if (byIndex == -1) {
                throw new DukeException("A deadline requires a '/by' parameter. Use: deadline [description] /by [yyyy-MM-dd]");
            }
            String deadlineDesc = arguments.substring(0, byIndex).trim();
            String byString = arguments.substring(byIndex + 3).trim();
            if (deadlineDesc.isEmpty()) {
                throw new DukeException("The description of a deadline cannot be empty.");
            }
            if (byString.isEmpty()) {
                throw new DukeException("The deadline date cannot be empty.");
            }
            
            // Try parsing to LocalDate
            try {
                java.time.LocalDate byDate = java.time.LocalDate.parse(byString);
                Task newDeadline = new Deadline(deadlineDesc, byDate);
                tasks.add(newDeadline);
                printAddedConfirmation(newDeadline, tasks.size());
            } catch (java.time.format.DateTimeParseException e) {
                throw new DukeException("Please provide the deadline date in yyyy-MM-dd format (e.g., 2019-12-02).");
            }
            break;

        case EVENT:
            if (arguments.isEmpty()) {
                throw new DukeException("The description of an event cannot be empty. Use: event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");
            }
            int fromIndex = arguments.indexOf("/from");
            int toIndex = arguments.indexOf("/to");
            if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex) {
                throw new DukeException("An event requires both '/from' and '/to' parameters. Use: event [description] /from [yyyy-MM-dd] /to [yyyy-MM-dd]");
            }
            String eventDesc = arguments.substring(0, fromIndex).trim();
            String fromString = arguments.substring(fromIndex + 5, toIndex).trim();
            String toString = arguments.substring(toIndex + 3).trim();
            if (eventDesc.isEmpty()) {
                throw new DukeException("The description of an event cannot be empty.");
            }
            if (fromString.isEmpty() || toString.isEmpty()) {
                throw new DukeException("The start and end dates of an event cannot be empty.");
            }
            
            // Try parsing to LocalDate
            try {
                java.time.LocalDate fromDate = java.time.LocalDate.parse(fromString);
                java.time.LocalDate toDate = java.time.LocalDate.parse(toString);
                Task newEvent = new Event(eventDesc, fromDate, toDate);
                tasks.add(newEvent);
                printAddedConfirmation(newEvent, tasks.size());
            } catch (java.time.format.DateTimeParseException e) {
                throw new DukeException("Please provide event dates in yyyy-MM-dd format (e.g., 2019-12-02).");
            }
            break;

        case UNMARK:
            if (arguments.isEmpty()) {
                throw new DukeException("Please specify the task number to unmark. Use: unmark [index]");
            }
            try {
                int index = Integer.parseInt(arguments) - 1;
                if (index < 0 || index >= tasks.size()) {
                    throw new DukeException("Task number out of range. You currently have " + tasks.size() + " tasks.");
                }
                Task task = tasks.get(index);
                task.unmarkAsDone();
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + task);
            } catch (NumberFormatException e) {
                throw new DukeException("The task number must be a valid integer.");
            }
            break;

        case DELETE:
            if (arguments.isEmpty()) {
                throw new DukeException("Please specify the task number to delete. Use: delete [index]");
            }
            try {
                int index = Integer.parseInt(arguments) - 1;
                if (index < 0 || index >= tasks.size()) {
                    throw new DukeException("Task number out of range. You currently have " + tasks.size() + " tasks.");
                }
                Task removedTask = tasks.remove(index);
                System.out.println(" Noted. I've removed this task:");
                System.out.println("   " + removedTask);
                System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            } catch (NumberFormatException e) {
                throw new DukeException("The task number must be a valid integer.");
            }
            break;

        case UNKNOWN:
        default:
            throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }

    private static void printAddedConfirmation(Task task, int taskCount) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
    }
}