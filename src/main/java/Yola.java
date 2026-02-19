import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.FileWriter;

public class Yola {

    private static final ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {

        printWelcomeMessage();

        // File path with cross-platform support
        String home = System.getProperty("user.home");
        // File path format is C:/Users/user/yola/data
        Path filePath = Paths.get(home, "yola", "data", "yola.txt");

        try {
            loadFile(filePath);
        } catch (IOException e) {
            System.out.println("    Error creating file: " + e.getMessage());
            System.out.println("    Exiting.......");
            return;
        }

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
                saveToFile(filePath);
                break;
            case "unmark":
                unmarkTask(commandBody);
                saveToFile(filePath);
                break;
            case "delete":
                deleteTask(commandBody);
                saveToFile(filePath);
                break;
            case "todo":
                // get the string after "todo "
                String todoDescription = commandBody.trim();
                if (todoDescription.isEmpty()) {
                    printDivider();
                    System.out.println("     no no no... The description must not be empty. Pls try again");
                    printDivider();
                    break;
                }
                Todo t = new Todo(todoDescription.strip());
                tasks.add(t);
                saveToFile(filePath);
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
                tasks.add(d);
                saveToFile(filePath);
                printTask(d);
                break;
            case "event":
                line = line.substring(6).strip();
                String eventDescription = line.split(" /from ")[0];
                String from = line.split(" /from ")[1].split(" /to ")[0];
                String to = line.split(" /from ")[1].split(" /to ")[1];
                Event e = new Event(eventDescription, from, to);
                tasks.add(e);
                saveToFile(filePath);
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

    private static void printWelcomeMessage() {
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
    }
    public static void printTasks() {
        printDivider();
        System.out.println("    Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i += 1) {
            System.out.println("    " + (i + 1) + "." + tasks.get(i).toString());
        }
        printDivider();
    }

    public static void printTask(Task t) {
        printDivider();
        System.out.println("    Got it. I've added this task:");
        System.out.println("      " + t.toString());
        System.out.println("    Now you have " + tasks.size() + " tasks in the list.");
        printDivider();

    }

    private static void markTask(String commandBody) {

        try {
            int taskNum = Integer.parseInt(commandBody);
            // Check for out of bound
            if (taskNum > tasks.size()) {
                throw new IndexOutOfBoundsException();
            }

            Task t = tasks.get(taskNum - 1);
            t.markDone();
            System.out.println("    Nice! I've marked this task as done:");
            System.out.println("      " + t);
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
            if (taskNum > tasks.size()) {
                throw new IndexOutOfBoundsException();
            }
            Task t = tasks.get(taskNum - 1);
            t.markUndone();
            printDivider();
            System.out.println("        OK, I've marked this task as not done yet:");
            System.out.println("      " + t);
            printDivider();
        } catch (NumberFormatException e) {
            System.out.println("Please enter a task number");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("The task number you entered is out of bound, please try again with a valid number");
        }

    }

    public static void deleteTask(String commandBody) {
        try {
            int taskNum = Integer.parseInt(commandBody);
            // Check for out of bound
            if (taskNum > tasks.size()) {
                throw new IndexOutOfBoundsException();
            }
            int taskIndex = taskNum - 1;
            Task t = tasks.get(taskIndex);
            // String of task description to print after deleting
            String taskString = t.toString();
            tasks.remove(taskIndex);

            // Print successfully deletion message
            printDivider();
            System.out.println("        Roger! Successfully delete the task:");
            System.out.println("      " + taskString);
            System.out.println("        Now you have " + tasks.size() + " tasks remaining in the list.");
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

    private static void loadFile(Path path) throws IOException {

        // Check whether the file exists
        if (!Files.exists(path)) {

            Files.createDirectories(path.getParent());
            Files.createFile(path);
            System.out.println("    Created file yola.txt at: " + path);
        }

        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(path));

        for (String line: lines){
            Task t = parseTask(line);
            if (t != null){
                tasks.add(t);
            }
        }
    }

    private static Task parseTask(String line){

        String[] words = line.split(" \\| ");
        String command = words[0];
        boolean isDone = words[1].equals("1");

        switch (command) {
        case "T":
            String todoDescription = words[2];
            Todo t = new Todo(todoDescription);
            if (isDone){
                t.markDone();
            }
            return t;
        case "D":
            String deadlineDescription = words[2];
            String deadlineBy = words[3];
            Deadline d = new Deadline(deadlineDescription, deadlineBy);
            if (isDone){
                d.markDone();
            }
            return d;
        case "E":
            String eventDescription = words[2];
            String eventFrom = words[3];
            String eventTo = words[4];
            Event e = new Event(eventDescription, eventFrom, eventTo);
            if (isDone){
                e.markDone();
            }
            return e;
        default:
            return null;
        }
    }

    private static void saveToFile(Path path) {
        try (FileWriter fw = new FileWriter(path.toString())){
            for (Task t: tasks){
                fw.write(t.getFileText());
                fw.write(System.lineSeparator());
            }

        } catch (IOException e){
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
