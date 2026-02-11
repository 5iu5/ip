import java.util.Scanner;

public class Yola {
    private static final Task[] tasks = new Task[100];
    private static int size = 0;


    public static void printTasks() {
        System.out.println("    ____________________________________________________________");
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < size; i += 1) {
            System.out.println("    " + (i + 1) + "." + tasks[i].toString());
        }
        System.out.println("    ____________________________________________________________");
    }

    public static void markTask(int n) {
        Task t = tasks[n - 1];
        t.markDone();

        System.out.println("    ____________________________________________________________");
        System.out.println("    Nice! I've marked this task as done:");
        System.out.println("      " + "[" + t.getStatusIcon() + "] " + t.getDescription());
        System.out.println("    ____________________________________________________________");
    }

    public static void unmarkTask(int n) {
        Task t = tasks[n - 1];
        t.markUndone();

        System.out.println("    ____________________________________________________________");
        System.out.println("        OK, I've marked this task as not done yet:");
        System.out.println("      " + "[" + t.getStatusIcon() + "] " + t.getDescription());
        System.out.println("    ____________________________________________________________");
    }

    public static void printTask(Task t) {
        System.out.println("    ____________________________________________________________");
        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + t.toString());
        System.out.println("    Now you have " + size + " tasks in the list.");
        System.out.println("    ____________________________________________________________");

    }

    public static void main(String[] args) {

        String logo = " __   __      _       \n"
                + " \\ \\ / /___  | | __ _ \n"
                + "  \\ V // _ \\ | |/ _` |\n"
                + "   | || (_) || | (_| |\n"
                + "   |_| \\___/ |_|\\__,_|\n";

        System.out.println("Hello from\n" + logo);
        System.out.println("    ____________________________________________________________");
        System.out.println("    Hello! I'm Yola");
        System.out.println("    What can I do for you?");
        System.out.println("    ____________________________________________________________");

        String line;
        Scanner in = new Scanner(System.in);

        while (true) {
            line = in.nextLine();

            if (line.equals("bye")) {
                break;
            }

            String[] words = line.strip().split("\\s+");
            //validation for mark and unmark's 2nd argument
            boolean isValidNum = false;
            int taskNum = -1;
            if (words.length >= 2) {
                try {
                    taskNum = Integer.parseInt(words[1]);
                    if (taskNum >= 1 && taskNum <= size) {
                        isValidNum = true;
                    }
                } catch (NumberFormatException e) {
                    isValidNum = false;
                }
            }

            switch (words[0]) {
            case "list":
                printTasks();
                break;
            case "mark":
                if (!isValidNum) {
                    System.out.println("Invalid task!");
                    break;
                }
                markTask(taskNum);
                break;
            case "unmark":
                if (!isValidNum) {
                    System.out.println("Invalid task!");
                    break;
                }
                unmarkTask(taskNum);
                break;
            case "todo":
                // get the string after "todo "
                String description = line.substring(4);
                if (description.isEmpty()){
                    System.out.println("    ____________________________________________________________");
                    System.out.println("     no no no... The description must not be empty. Pls try again");
                    System.out.println("    ____________________________________________________________");
                    break;
                }
                Todo t = new Todo(description.strip());
                tasks[size] = t;
                size += 1;
                printTask(t);
                break;
            case "deadline":
                String[] parts = line.substring(9).split(" /by ");
                Deadline d = new Deadline(parts[0], parts[1]);
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
                System.out.println("    ____________________________________________________________");
                System.out.println("    What was that? I don't quite understand");
                System.out.println("    ____________________________________________________________");
                break;
            }

        }
        System.out.println("    ____________________________________________________________");
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");
    }
}
