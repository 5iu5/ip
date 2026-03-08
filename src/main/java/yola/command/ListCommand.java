package yola.command;

import yola.model.TaskList;
import yola.storage.StorageFile;
import yola.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, StorageFile storage) {
        ui.printTasks(tasks.getTasks());
    }
}