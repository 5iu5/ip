package yola.command;

import yola.exception.YolaException;
import yola.model.TaskList;
import yola.storage.StorageFile;
import yola.task.Task;
import yola.ui.Ui;

import java.util.ArrayList;

public class FindCommand extends Command{
    private final String keyword;

    public FindCommand(String keyword){
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, StorageFile storage){
        try {
            if (keyword.isEmpty()) {
                throw new YolaException("Keyword field is empty! Please enter a keyword to lookup.");
            }

            ArrayList<Task> matchingTasks = tasks.findTasks(keyword);
            ui.printMatchingTasks(matchingTasks);

        } catch (YolaException e) {
            ui.printMessage(e.getMessage());
        }
    }
}
