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
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.FileWriter;

public class Yola {
    private static StorageFile storage;
    private static Ui ui = new Ui();
    private static ArrayList<Task> tasks;

    public Yola(String filePath){
        //instantiate ui
        ui = new Ui();
        storage = new StorageFile(filePath);
        ui.printLine("Entered here");
        try {
            tasks = storage.loadFile();
            ui.printLine("Success loading file");
        } catch (IOException e) {
            ui.printLine("Error loading file: " + e.getMessage());
            tasks = new ArrayList<>();

        }
//        try {
//            tasks = new TaskList(storage.load());
//        } catch (DukeException e) {
//            ui.showLoadingError();
//            tasks = new TaskList();
//        }
    }

    public static void main(String[] args) {
        final String STORAGE_FILEPATH = "./data/yola.txt";
        new Yola(STORAGE_FILEPATH);

        ui.printWelcomeMessage();

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
                try {
                    storage.saveToFile(tasks);
                } catch (IOException error) {
                    ui.printMessage("Error saving file: " + error.getMessage());
                }
                break;
            case "unmark":
                unmarkTask(commandBody);
                try {
                    storage.saveToFile(tasks);
                } catch (IOException error) {
                    ui.printMessage("Error saving file: " + error.getMessage());
                }
                break;
            case "delete":
                deleteTask(commandBody);
                try {
                    storage.saveToFile(tasks);
                } catch (IOException error) {
                    ui.printMessage("Error saving file: " + error.getMessage());
                }
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
                try {
                    storage.saveToFile(tasks);
                } catch (IOException error) {
                    ui.printMessage("Error saving file: " + error.getMessage());
                }
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
                try {
                    storage.saveToFile(tasks);
                } catch (IOException error) {
                    ui.printMessage("Error saving file: " + error.getMessage());
                }
                ui.printTaskAdded(d, tasks.size());
                break;
            case "event":
                line = line.substring(6).strip();
                String eventDescription = line.split(" /from ")[0];
                String from = line.split(" /from ")[1].split(" /to ")[0];
                String to = line.split(" /from ")[1].split(" /to ")[1];
                Event e = new Event(eventDescription, from, to);
                tasks.add(e);
                try {
                    storage.saveToFile(tasks);
                } catch (IOException error) {
                    ui.printMessage("Error saving file: " + error.getMessage());
                }
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
}
