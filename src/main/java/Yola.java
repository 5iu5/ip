import java.util.Scanner;

public class Yola {
    private static final Task[] tasks = new Task[100];
    private static int size = 0;

    public static void addTask(String description) {
        Task t = new Task(description);
        tasks[size] = t;
        size += 1;

        System.out.println("    ____________________________________________________________");
        System.out.println("     added: " + description);
        System.out.println("    ____________________________________________________________");
    }

    public static void printTasks() {
        System.out.println("    ____________________________________________________________");
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < size; i += 1) {
            System.out.println("    " + (i + 1) + ".[" + tasks[i].getStatusIcon() + "] " + tasks[i].description);
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
            default:
                addTask(line);
                break;
            }

        }
        System.out.println("    ____________________________________________________________");
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");
    }
}
