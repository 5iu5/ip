package yola.command;

import yola.model.TaskList;
import yola.storage.StorageFile;
import yola.task.Task;
import yola.ui.Ui;

import java.io.IOException;


public class UnmarkCommand extends Command{
    private final String commandBody;

    public UnmarkCommand(String commandBody) {
        this.commandBody = commandBody;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, StorageFile storage) {
        try {
            int taskNum = Integer.parseInt(commandBody);

            if (taskNum < 1 || taskNum > tasks.size()) {
                throw new IndexOutOfBoundsException();
            }

            Task task = tasks.get(taskNum - 1);
            task.markUndone();
            ui.printTaskUnmarked(task);
            storage.saveToFile(tasks);

        } catch (NumberFormatException e) {
            ui.printMessage("Please enter a valid task number.");
        } catch (IndexOutOfBoundsException e) {
            ui.printMessage("The task number entered is out of bounds.");
        } catch (IOException e) {
            ui.printMessage("Error saving file: " + e.getMessage());
        }
    }

}
