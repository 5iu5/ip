package yola.command;

import yola.exception.YolaException;
import yola.model.TaskList;
import yola.storage.StorageFile;
import yola.task.Todo;
import yola.ui.Ui;

import java.io.IOException;

public class TodoCommand extends Command{
    private final String commandBody;

    public TodoCommand(String commandBody) {
        this.commandBody = commandBody;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, StorageFile storage) {
        try {
            String description = commandBody.trim();

            if (description.isEmpty()) {
                throw new YolaException("The description of a todo command must not be empty.");
            }

            Todo todo = new Todo(description);
            tasks.add(todo);

            storage.saveToFile(tasks);
            ui.printTaskAdded(todo, tasks.size());

        } catch (YolaException e) {
            ui.printMessage(e.getMessage());
        } catch (IOException e) {
            ui.printMessage("Error saving file: " + e.getMessage());
        }
    }
}
