package yola.ui;

import yola.task.Task;

import java.util.ArrayList;

public class Ui {

    private static final String INDENT = "    ";

    public void printLine(String msg) {
        System.out.println(INDENT + msg);
    }

    public void printDivider() {
        printLine("____________________________________________________________");
    }

    public void printMessage(String msg) {
        printDivider();
        printLine(msg);
        printDivider();
    }

    public void printWelcomeMessage() {
        String logo = " __   __      _       \n"
                + " \\ \\ / /___  | | __ _ \n"
                + "  \\ V // _ \\ | |/ _` |\n"
                + "   | || (_) || | (_| |\n"
                + "   |_| \\___/ |_|\\__,_|\n";

        System.out.println("Hello from\n" + logo);
        printDivider();
        printLine("Hello! I'm Yola, your personal chatbot");
        printLine("What can I do for you?");
        printDivider();
    }

    public void printGoodbye() {
        printMessage("Bye. Hope to see you again soon!");
    }

    public void printTasks(ArrayList<Task> tasks) {
        printDivider();
        printLine("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i += 1) {
            printLine((i + 1) + "." + tasks.get(i).toString());
        }
        printDivider();
    }

    public void printTaskAdded(Task t, int tasksSize) {
        printDivider();
        printLine("Got it. I've added this task:");
        printLine(t.toString());
        printLine("Now you have " + tasksSize + " tasks in the list.");
        printDivider();
    }

    public void printTaskDeleted(Task t, int tasksSize) {
        printDivider();
        printLine("Roger! Successfully delete the task:");
        printLine(t.toString());
        printLine("Now you have " + tasksSize + " tasks remaining in the list.");
        printDivider();
    }

    public void printTaskMarked(Task t) {
        printDivider();
        printLine("Nice! I've marked this task as done:");
        printLine(t.toString());
        printDivider();
    }

    public void printTaskUnmarked(Task t) {
        printDivider();
        printLine("OK, I've marked this task as not done yet:");
        printLine(t.toString());
        printDivider();
    }

    public void printInvalidCommand() {
        printDivider();
        printLine("What was that? I don't quite understand. Available commands");
        printLine("These are the valid commands: list, todo, deadline, event, mark, unmark, delete, bye");
        printDivider();
    }

    public void printLoadingError(String errorMessage) {
        printLine("Error loading file: " + errorMessage);
    }

    public void printMatchingTasks(ArrayList<Task> tasks) {
        printDivider();
        if (tasks.isEmpty()){
            printLine("There are no matching tasks...");
        }
        else {
            printLine("Here are the matching tasks in your list:");
            for (int i = 0; i < tasks.size(); i += 1) {
                printLine((i + 1) + "." + tasks.get(i).toString());
            }
        }
        printDivider();
    }

}
