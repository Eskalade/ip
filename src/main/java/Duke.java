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

            if (input.equalsIgnoreCase("list")) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(" " + (i + 1) + "." + tasks.get(i));
                }
            } else if (input.startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(input.substring(5).trim()) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task task = tasks.get(index);
                        task.markAsDone();
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + task);
                    } else {
                        System.out.println(" Invalid task number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Please provide a valid task number to mark.");
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(input.substring(7).trim()) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task task = tasks.get(index);
                        task.unmarkAsDone();
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + task);
                    } else {
                        System.out.println(" Invalid task number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Please provide a valid task number to unmark.");
                }
            } else if (input.startsWith("todo ")) {
                String description = input.substring(5).trim();
                Task newTodo = new Todo(description);
                tasks.add(newTodo);
                printAddedConfirmation(newTodo, tasks.size());
            } else if (input.startsWith("deadline ")) {
                int byIndex = input.indexOf("/by ");
                if (byIndex != -1) {
                    String description = input.substring(9, byIndex).trim();
                    String by = input.substring(byIndex + 4).trim();
                    Task newDeadline = new Deadline(description, by);
                    tasks.add(newDeadline);
                    printAddedConfirmation(newDeadline, tasks.size());
                } else {
                    System.out.println(" Use format: deadline [description] /by [time]");
                }
            } else if (input.startsWith("event ")) {
                int fromIndex = input.indexOf("/from ");
                int toIndex = input.indexOf("/to ");
                if (fromIndex != -1 && toIndex != -1 && fromIndex < toIndex) {
                    String description = input.substring(6, fromIndex).trim();
                    String from = input.substring(fromIndex + 6, toIndex).trim();
                    String to = input.substring(toIndex + 4).trim();
                    Task newEvent = new Event(description, from, to);
                    tasks.add(newEvent);
                    printAddedConfirmation(newEvent, tasks.size());
                } else {
                    System.out.println(" Use format: event [description] /from [start] /to [end]");
                }
            } else {
                System.out.println(" Unknown command. Please use todo, deadline, event, list, mark, unmark, or bye.");
            }

            System.out.println(divider);
        }

        // Exit
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(divider);

        scanner.close();
    }

    private static void printAddedConfirmation(Task task, int taskCount) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
    }
}