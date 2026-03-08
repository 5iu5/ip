package yola.command;

import yola.model.TaskList;
import yola.storage.StorageFile;
import yola.ui.Ui;

public class ExitCommand extends Command{
    @Override
    public void execute(TaskList tasks, Ui ui, StorageFile storage) {
        ui.printGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
