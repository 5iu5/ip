package yola.command;

import yola.exception.YolaException;
import yola.model.TaskList;
import yola.storage.StorageFile;
import yola.task.Deadline;
import yola.ui.Ui;

import java.io.IOException;

/**
 * Represents a command that adds a deadline task to the task list.
 */
public class DeadlineCommand extends Command {
    private final String commandBody;

    public DeadlineCommand(String commandBody) {
        this.commandBody = commandBody;
    }

    /**
     * Executes the deadline command by parsing the input,
     * creating a deadline task, adding it to the task list,
     * and saving the updated list.
     *
     * @param tasks the task list to add the deadline task to
     * @param ui the ui object to display output
     * @param storage the storage handler used to save the updated task list
     */
    @Override
    public void execute(TaskList tasks, Ui ui, StorageFile storage) {
        try {
            String[] parts = parseDeadlineParts(commandBody);
            String description = parts[0];
            String by = parts[1];

            Deadline deadline = new Deadline(description, by);
            tasks.add(deadline);

            storage.saveToFile(tasks);
            ui.printTaskAdded(deadline, tasks.size());

        } catch (YolaException e) {
            ui.printMessage(e.getMessage());
        } catch (IOException e) {
            ui.printMessage("Error saving file: " + e.getMessage());
        }
    }

    /**
     * Parses the command body into a deadline description and deadline string.
     *
     * @param body the command body to parse
     * @return a string array where index 0 is the description
     *         and index 1 is the deadline
     * @throws YolaException if the command body is empty, invalid, or has an empty description or deadline
     */
    private String[] parseDeadlineParts(String body) throws YolaException {
        if (body.trim().isEmpty()) {
            throw new YolaException("The description and deadline must not be empty.");
        }

        if (!body.contains("/by")) {
            throw new YolaException("Invalid command format. Use: deadline [description] /by [deadline]");
        }

        String[] parts = body.split("/by", 2);
        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty()) {
            throw new YolaException("The description of a deadline must not be empty.");
        }

        if (by.isEmpty()) {
            throw new YolaException("The deadline must not be empty.");
        }

        return new String[]{description, by};
    }
}