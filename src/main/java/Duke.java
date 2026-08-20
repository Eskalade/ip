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

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("bye")) {
                break;
            }

            System.out.println(divider);

            try {
                processCommand(input, tasks);
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

    private static void processCommand(String input, ArrayList<Task> tasks) throws DukeException {
        if (input.equalsIgnoreCase("list")) {
            if (tasks.isEmpty()) {
                System.out.println(" Your list is currently empty.");
                return;
            }
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i));
            }
        } else if (input.equals("todo") || input.startsWith("todo ")) {
            if (input.equals("todo")) {
                throw new DukeException("The description of a todo cannot be empty. Use: todo [description]");
            }
            String description = input.substring(5).trim();
            if (description.isEmpty()) {
                throw new DukeException("The description of a todo cannot be empty.");
            }
            Task newTodo = new Todo(description);
            tasks.add(newTodo);
            printAddedConfirmation(newTodo, tasks.size());

        } else if (input.equals("deadline") || input.startsWith("deadline ")) {
            if (input.equals("deadline")) {
                throw new DukeException("The description of a deadline cannot be empty. Use: deadline [description] /by [time]");
            }
            int byIndex = input.indexOf("/by");
            if (byIndex == -1) {
                throw new DukeException("A deadline requires a '/by' parameter. Use: deadline [description] /by [time]");
            }
            String description = input.substring(9, byIndex).trim();
            String by = input.substring(byIndex + 3).trim();
            if (description.isEmpty()) {
                throw new DukeException("The description of a deadline cannot be empty.");
            }
            if (by.isEmpty()) {
                throw new DukeException("The deadline date/time cannot be empty.");
            }
            Task newDeadline = new Deadline(description, by);
            tasks.add(newDeadline);
            printAddedConfirmation(newDeadline, tasks.size());

        } else if (input.equals("event") || input.startsWith("event ")) {
            if (input.equals("event")) {
                throw new DukeException("The description of an event cannot be empty. Use: event [description] /from [start] /to [end]");
            }
            int fromIndex = input.indexOf("/from");
            int toIndex = input.indexOf("/to");
            if (fromIndex == -1 || toIndex == -1 || fromIndex > toIndex) {
                throw new DukeException("An event requires both '/from' and '/to' parameters. Use: event [description] /from [start] /to [end]");
            }
            String description = input.substring(6, fromIndex).trim();
            String from = input.substring(fromIndex + 5, toIndex).trim();
            String to = input.substring(toIndex + 3).trim();
            if (description.isEmpty()) {
                throw new DukeException("The description of an event cannot be empty.");
            }
            if (from.isEmpty() || to.isEmpty()) {
                throw new DukeException("The start and end times of an event cannot be empty.");
            }
            Task newEvent = new Event(description, from, to);
            tasks.add(newEvent);
            printAddedConfirmation(newEvent, tasks.size());

        } else if (input.equals("mark") || input.startsWith("mark ")) {
            if (input.equals("mark")) {
                throw new DukeException("Please specify the task number to mark as done. Use: mark [index]");
            }
            try {
                int index = Integer.parseInt(input.substring(5).trim()) - 1;
                if (index < 0 || index >= tasks.size()) {
                    throw new DukeException("Task number out of range. You currently have " + tasks.size() + " tasks.");
                }
                Task task = tasks.get(index);
                task.markAsDone();
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + task);
            } catch (NumberFormatException e) {
                throw new DukeException("The task number must be a valid integer.");
            }

        } else if (input.equals("unmark") || input.startsWith("unmark ")) {
            if (input.equals("unmark")) {
                throw new DukeException("Please specify the task number to unmark. Use: unmark [index]");
            }
            try {
                int index = Integer.parseInt(input.substring(7).trim()) - 1;
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

        } else if (input.equals("delete") || input.startsWith("delete ")) {
            if (input.equals("delete")) {
                throw new DukeException("Please specify the task number to delete. Use: delete [index]");
            }
            try {
                int index = Integer.parseInt(input.substring(7).trim()) - 1;
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

        } else {
            throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }

    private static void printAddedConfirmation(Task task, int taskCount) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
    }
}