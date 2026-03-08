package yola.command;

import yola.exception.YolaException;
import yola.model.TaskList;
import yola.storage.StorageFile;
import yola.task.Deadline;
import yola.ui.Ui;

import java.io.IOException;

public class DeadlineCommand extends Command {
    private final String commandBody;

    public DeadlineCommand(String commandBody) {
        this.commandBody = commandBody;
    }

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