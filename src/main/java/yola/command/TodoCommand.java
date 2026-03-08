package yola.command;

import yola.exception.YolaException;
import yola.model.TaskList;
import yola.storage.StorageFile;
import yola.task.Todo;
import yola.ui.Ui;

import java.io.IOException;

/**
 * Represents a command that adds a todo task to the task list.
 */
public class TodoCommand extends Command{
    private final String commandBody;

    public TodoCommand(String commandBody) {
        this.commandBody = commandBody;
    }

    /**
     * Executes the todo command by validating the input,
     * creating a todo task, adding it to the task list, and
     * saving the updated list.
     *
     * @param tasks the task list to add the todo task to
     * @param ui the user interface used to display output
     * @param storage the storage handler used to save the updated task list
     */
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
