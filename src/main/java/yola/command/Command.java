package yola.command;

import yola.model.TaskList;
import yola.storage.StorageFile;
import yola.ui.Ui;


public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, StorageFile storage);

    public boolean isExit() {
        return false;
    }
}
