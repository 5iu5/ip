package yola;

import yola.command.Command;
import yola.model.TaskList;
import yola.parser.Parser;
import yola.storage.StorageFile;
import yola.ui.Ui;

import java.io.IOException;
import java.util.Scanner;

/**
 * Entry point and main controller for the Yola chatbot application.
 * This class initializes the user interface, storage, and task list,
 * then runs the main command loop to process user input.
 */
public class Yola {
    private static StorageFile storage;
    private static Ui ui = new Ui();
    private static TaskList tasks;

    /**
     * Creates a Yola chatbot instance using the given storage file path.
     * <p>
     * If the file cannot be loaded, an empty task list is created instead.
     *
     * @param filePath the path to the storage file used to load and save tasks
     */
    public Yola(String filePath){
        //instantiate ui
        ui = new Ui();
        storage = new StorageFile(filePath);
        try {
            tasks = storage.loadFile();
        } catch (IOException e) {
            ui.printLoadingError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Starts the chatbot and runs until an exit command is entered by the user.
     */
    public void run() {
        ui.printWelcomeMessage();

        Scanner in = new Scanner(System.in);

        while (true) {
            String line = in.nextLine();
            Command command = Parser.parse(line);
            command.execute(tasks, ui, storage);

            if (command.isExit()) {
                break;
            }
        }
    }

    /**
     * Launches the Yola chatbot application.
     *
     * @param args command-line arguments passed to the program
     */
    public static void main(String[] args) {
        final String STORAGE_FILEPATH = "./data/yola.txt";
        new Yola(STORAGE_FILEPATH).run();
    }

}
