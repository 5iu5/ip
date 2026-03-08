package yola;

import yola.command.Command;
import yola.model.TaskList;
import yola.parser.Parser;
import yola.storage.StorageFile;
import yola.ui.Ui;


import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;


public class Yola {
    private static StorageFile storage;
    private static Ui ui = new Ui();
    private static TaskList tasks;

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
            tasks = new TaskList();

        }

    }

    public static void main(String[] args) {
        final String STORAGE_FILEPATH = "./data/yola.txt";
        new Yola(STORAGE_FILEPATH);

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

}
