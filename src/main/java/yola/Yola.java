package yola;

import yola.exception.YolaException;
import yola.storage.StorageFile;
import yola.task.Deadline;
import yola.task.Event;
import yola.task.Task;
import yola.task.Todo;
import yola.ui.Ui;

import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.FileWriter;

public class Yola {
    private static Ui ui = new Ui();
    private static final ArrayList<Task> tasks = new ArrayList<>();

    public Yola(String filePath){
        //instantiate ui
        ui = new Ui();
//        storage = new Storage(filePath);
//        try {
//            tasks = new TaskList(storage.load());
//        } catch (DukeException e) {
//            ui.showLoadingError();
//            tasks = new TaskList();
//        }
    }

    public static void main(String[] args) {

        ui.printWelcomeMessage();

        // File path with cross-platform support
        String home = System.getProperty("user.home");
        // File path format is C:/Users/user/yola/data
        Path filePath = Paths.get(home, "yola", "data", "yola.txt");

        try {
            loadFile(filePath);
        } catch (IOException e) {
            ui.printLine("Error creating file: " + e.getMessage());
            ui.printLine("Exiting.......");
            return;
        }

        String line;
        Scanner in = new Scanner(System.in);

        while (true) {

            line = in.nextLine();
            if (line.equals("bye")) {
                ui.printGoodbye();
                break;
            }

            // Split user input into command word and the remaining line
            String[] commands = line.strip().split("\\s+", 2);
            String commandWord = commands[0];
            String commandBody = (commands.length > 1) ? commands[1] : "";

            switch (commandWord) {
            case "list":
                ui.printTasks(tasks);
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
                    ui.printMessage("no no no... The description must not be empty. Pls try again");
                    break;
                }
                Todo t = new Todo(todoDescription.strip());
                tasks.add(t);
                saveToFile(filePath);
                ui.printTaskAdded(t, tasks.size());
                break;
            case "deadline":
                String deadlineDescription;
                String deadlineBy;
                try {
                    deadlineDescription = Deadline.getDescription(commandBody);
                    deadlineBy = Deadline.getDeadline(commandBody);
                } catch (YolaException e) {
                    ui.printMessage(e.getMessage());
                    break;
                }
                Deadline d = new Deadline(deadlineDescription, deadlineBy);
                tasks.add(d);
                saveToFile(filePath);
                ui.printTaskAdded(d, tasks.size());
                break;
            case "event":
                line = line.substring(6).strip();
                String eventDescription = line.split(" /from ")[0];
                String from = line.split(" /from ")[1].split(" /to ")[0];
                String to = line.split(" /from ")[1].split(" /to ")[1];
                Event e = new Event(eventDescription, from, to);
                tasks.add(e);
                saveToFile(filePath);
                ui.printTaskAdded(e, tasks.size());
                break;

            default:
                ui.printMessage("What was that? I don't quite understand");
                break;
            }
        }
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
            ui.printTaskMarked(t);

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
            ui.printTaskUnmarked(t);

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
            ui.printTaskDeleted(t, tasks.size());

        } catch (NumberFormatException e) {
            System.out.println("Please enter a task number");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("The task number you entered is out of bound, please try again with a valid number");
        }
    }


    private static void loadFile(Path path) throws IOException {

        // Check whether the file exists
        if (!Files.exists(path)) {

            Files.createDirectories(path.getParent());
            Files.createFile(path);
            ui.printLine("Created file yola.txt at: " + path);
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
            ui.printLine("Error writing to file: " + e.getMessage());
        }
    }
}
