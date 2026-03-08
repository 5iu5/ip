package yola.command;

import yola.model.TaskList;
import yola.storage.StorageFile;
import yola.ui.Ui;

public class InvalidCommand extends Command{
    @Override
    public void execute(TaskList tasks, Ui ui, StorageFile storage) {
        ui.printInvalidCommand();
    }
}
