package yola.command;

import yola.storage.StorageFile;
import yola.task.Task;
import yola.ui.Ui;

import java.util.ArrayList;

public class InvalidCommand extends Command{
    @Override
    public void execute(ArrayList<Task> tasks, Ui ui, StorageFile storage) {
        ui.printInvalidCommand();
    }
}
