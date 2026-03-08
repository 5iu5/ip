package yola.command;

import yola.exception.YolaException;
import yola.storage.StorageFile;
import yola.task.Event;
import yola.task.Task;
import yola.ui.Ui;

import java.io.IOException;
import java.util.ArrayList;

public class EventCommand extends Command {
    private final String commandBody;

    public EventCommand(String commandBody) {
        this.commandBody = commandBody;
    }

    @Override
    public void execute(ArrayList<Task> tasks, Ui ui, StorageFile storage) {
        try {
            String[] parts = parseEventParts(commandBody);
            String description = parts[0];
            String from = parts[1];
            String to = parts[2];

            Event event = new Event(description, from, to);
            tasks.add(event);

            storage.saveToFile(tasks);
            ui.printTaskAdded(event, tasks.size());

        } catch (YolaException e) {
            ui.printMessage(e.getMessage());
        } catch (IOException e) {
            ui.printMessage("Error saving file: " + e.getMessage());
        }
    }

    private String[] parseEventParts(String body) throws YolaException {
        if (body.trim().isEmpty()) {
            throw new YolaException("The description, /from, and /to fields must not be empty.");
        }

        if (!body.contains("/from")) {
            throw new YolaException("Invalid command format. Use: event [description] /from [start] /to [end]");
        }

        String[] fromSplit = body.split("/from", 2);
        String description = fromSplit[0].trim();

        if (description.isEmpty()) {
            throw new YolaException("The description of an event must not be empty.");
        }

        if (fromSplit.length < 2 || !fromSplit[1].contains("/to")) {
            throw new YolaException("Invalid command format. Use: event [description] /from [start] /to [end]");
        }

        String[] toSplit = fromSplit[1].split("/to", 2);
        String from = toSplit[0].trim();
        String to = toSplit[1].trim();

        if (from.isEmpty()) {
            throw new YolaException("The /from field must not be empty.");
        }

        if (to.isEmpty()) {
            throw new YolaException("The /to field must not be empty.");
        }

        return new String[]{description, from, to};
    }
}