package yola.ui;

import yola.task.Task;

import java.util.ArrayList;

/**
 * Handles all user-facing output for the Yola chatbot.
 * <p>
 * This class is responsible for printing messages, dividers,
 * task lists, and status updates to the console.
 */
public class Ui {

    private static final String INDENT = "    ";

    /**
     * Prints a single line with the standard indentation.
     *
     * @param msg the message to be printed
     */
    public void printLine(String msg) {
        System.out.println(INDENT + msg);
    }

    /**
     * Prints a divider line to separate sections of output response.
     */
    public void printDivider() {
        printLine("____________________________________________________________");
    }

    /**
     * Prints a message surrounded by divider lines.
     *
     * @param msg the message to be printed
     */
    public void printMessage(String msg) {
        printDivider();
        printLine(msg);
        printDivider();
    }

    /**
     * Prints the chatbot welcome message and logo.
     */
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

    /**
     * Prints the chatbot goodbye message.
     */
    public void printGoodbye() {
        printMessage("Bye Bye.... Hope to see you again soon!");
    }

    /**
     * Prints all tasks currently in the task list.
     *
     * @param tasks the list of tasks to be printed
     */
    public void printTasks(ArrayList<Task> tasks) {
        printDivider();
        printLine("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i += 1) {
            printLine((i + 1) + "." + tasks.get(i).toString());
        }
        printDivider();
    }

    /**
     * Prints a confirmation message after a task is added.
     *
     * @param t the task that was added
     * @param tasksSize the updated total number of tasks
     */
    public void printTaskAdded(Task t, int tasksSize) {
        printDivider();
        printLine("Got it. I've added this task:");
        printLine(t.toString());
        printLine("Now you have " + tasksSize + " tasks in the list.");
        printDivider();
    }

    /**
     * Prints a confirmation message after a task is deleted.
     *
     * @param t the task that was deleted
     * @param tasksSize the updated total number of remaining tasks
     */
    public void printTaskDeleted(Task t, int tasksSize) {
        printDivider();
        printLine("Roger! Successfully delete the task:");
        printLine(t.toString());
        printLine("Now you have " + tasksSize + " tasks remaining in the list.");
        printDivider();
    }

    /**
     * Prints a confirmation message after a task is marked as done.
     *
     * @param t the task that was marked
     */
    public void printTaskMarked(Task t) {
        printDivider();
        printLine("Nice! I've marked this task as done:");
        printLine(t.toString());
        printDivider();
    }

    /**
     * Prints a confirmation message after a task is unmarked.
     *
     * @param t the task that was unmarked
     */
    public void printTaskUnmarked(Task t) {
        printDivider();
        printLine("OK, I've marked this task as not done yet:");
        printLine(t.toString());
        printDivider();
    }

    /**
     * Prints a message indicating that the user entered an invalid command.
     */
    public void printInvalidCommand() {
        printDivider();
        printLine("What was that? I don't quite understand. Available commands");
        printLine("These are the valid commands: list, todo, deadline, event, mark, unmark, delete, bye");
        printDivider();
    }

    /**
     * Prints an error message shown when there is an error loading the storage file.
     *
     * @param errorMessage the error message to be displayed
     */
    public void printLoadingError(String errorMessage) {
        printLine("Error loading file: " + errorMessage);
    }

    /**
     * Prints the tasks whose descriptions match the user's search keyword.
     *
     * @param tasks the list of matching tasks
     */
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
