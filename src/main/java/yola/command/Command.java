package yola.command;

import yola.model.TaskList;
import yola.storage.StorageFile;
import yola.ui.Ui;

/**
 * Represents a command that can be executed by the Yola chatbot.
 */
public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, StorageFile storage);

    public boolean isExit() {
        return false;
    }
}
