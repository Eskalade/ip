import java.util.Scanner;

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

        // Echo loop
        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                break;
            }

            System.out.println(divider);
            System.out.println(" " + input);
            System.out.println(divider);
        }

        // Exit
        System.out.println(divider);
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(divider);

        scanner.close();
    }
}