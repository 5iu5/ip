import java.util.Scanner;
import java.util.Arrays;

public class Yola {
    private static final Task[] tasks = new Task[100];
    private static int size = 0;


    public static void printTasks() {
        printDivider();
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < size; i += 1) {
            System.out.println("    " + (i + 1) + "." + tasks[i].toString());
        }
        printDivider();
    }

    public static void printTask(Task t) {
        printDivider();
        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + t.toString());
        System.out.println("    Now you have " + size + " tasks in the list.");
        printDivider();

    }

    public static void main(String[] args) {

        String logo = " __   __      _       \n"
                + " \\ \\ / /___  | | __ _ \n"
                + "  \\ V // _ \\ | |/ _` |\n"
                + "   | || (_) || | (_| |\n"
                + "   |_| \\___/ |_|\\__,_|\n";

        System.out.println("Hello from\n" + logo);
        printDivider();
        System.out.println("    Hello! I'm Yola");
        System.out.println("    What can I do for you?");
        printDivider();

        String line;
        Scanner in = new Scanner(System.in);


        while (true) {

            line = in.nextLine();
            if (line.equals("bye")) {
                printGoodbye();
                break;
            }

            // Split user input into command word and the remaining line
            String[] commands = line.strip().split("\\s+", 2);
            String commandWord = commands[0];
            String commandBody = (commands.length > 1) ? commands[1] : "";

            switch (commandWord) {
            case "list":
                printTasks();
                break;
            case "mark":
                markTask(commandBody);
                break;
            case "unmark":
                unmarkTask(commandBody);
                break;


            case "todo":
                // get the string after "todo "
                String todoDescription = line.substring(4);
                if (todoDescription.isEmpty()) {
                    printDivider();
                    System.out.println("     no no no... The description must not be empty. Pls try again");
                    printDivider();
                    break;
                }
                Todo t = new Todo(todoDescription.strip());
                tasks[size] = t;
                size += 1;
                printTask(t);
                break;
            case "deadline":
                String deadlineDescription;
                String deadlineBy;
                try {
                    deadlineDescription = Deadline.getDescription(commandBody);
                    deadlineBy = Deadline.getDeadline(commandBody);
                } catch (YolaException e) {
                    printDivider();
                    System.out.println("    " + e.getMessage());
                    printDivider();
                    break;
                }
                Deadline d = new Deadline(deadlineDescription, deadlineBy);
                tasks[size] = d;
                size += 1;
                printTask(d);
                break;
            case "event":
                line = line.substring(6).strip();
                String eventDescription = line.split(" /from ")[0];
                String from = line.split(" /from ")[1].split(" /to ")[0];
                String to = line.split(" /from ")[1].split(" /to ")[1];
                Event e = new Event(eventDescription, from, to);
                tasks[size] = e;
                size += 1;
                printTask(e);
                break;

            default:
                printDivider();
                System.out.println("    What was that? I don't quite understand");
                printDivider();
                break;
            }

        }

    }
    private static void markTask(String commandBody) {

        try {
            int taskNum = Integer.parseInt(commandBody);
            // Check for out of bound
            if (taskNum >size) {
                throw new IndexOutOfBoundsException();
            }
            Task t = tasks[taskNum - 1];
            t.markDone();
            System.out.println("    Nice! I've marked this task as done:");
            System.out.println("      " + "[" + t.getStatusIcon() + "] " + t.getDescription());
            printDivider();
        } catch (NumberFormatException e) {
            System.out.println("Please enter a task number");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("The task number you entered is out of bound, please try again with a valid number");
        }

    }

    private static void unmarkTask(String commandBody) {

        try {
            int taskNum = Integer.parseInt(commandBody);
            // Check for out of bound
            if (taskNum >size) {
                throw new IndexOutOfBoundsException();
            }
            Task t = tasks[taskNum - 1];
            t.markUndone();
            printDivider();
            System.out.println("        OK, I've marked this task as not done yet:");
            System.out.println("      " + "[" + t.getStatusIcon() + "] " + t.getDescription());
            printDivider();
        } catch (NumberFormatException e) {
            System.out.println("Please enter a task number");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("The task number you entered is out of bound, please try again with a valid number");
        }

    }




    private static void printGoodbye() {
        printDivider();
        System.out.println("    Bye. Hope to see you again soon!");
        printDivider();
    }

    private static void printDivider() {
        System.out.println("    ____________________________________________________________");
    }
}
