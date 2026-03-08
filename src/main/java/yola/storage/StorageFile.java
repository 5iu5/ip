package yola.storage;

import yola.model.TaskList;
import yola.task.Deadline;
import yola.task.Event;
import yola.task.Task;
import yola.task.Todo;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * Handles loading tasks from and saving tasks to a file.
 * <p>
 * This class is responsible for reading stored task data,
 * converting each line into the corresponding task object,
 * and writing task data back to the storage file.
 */
public class StorageFile {

    private final Path filePath;

    /**
     * Creates a storage handler for the specified file path.
     *
     * @param filePathString the path to the storage file
     */
    public StorageFile(String filePathString) {
        this.filePath = Paths.get(filePathString);
    }

    /**
     * Loads tasks from the storage file into a {@code TaskList}.
     * <p>
     * If the file does not exist, the required directories and file
     * are created, and an empty task list is returned.
     *
     * @return the task list loaded from the file
     * @throws IOException if an error occurs while reading or creating the file
     */
    public TaskList loadFile() throws IOException {
        TaskList tasks = new TaskList();
        // Check whether the file exists
        if (!Files.exists(filePath)) {
            // Create the file if it does not exist
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.createFile(filePath);
            return tasks;
        }

        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(filePath));

        for (String line: lines){
            Task t = parseTask(line);
            if (t != null){
                tasks.add(t);
            }
        }
        return tasks;
    }

    private Task parseTask(String line){
        String[] words = line.split(" \\| ");
        String command = words[0];
        boolean isDone = words[1].equals("1");

        switch (command) {
        case "T":
            String todoDescription = words[2];
            Todo t = new Todo(todoDescription);
            if (isDone){
                t.markDone();
            }
            return t;
        case "D":
            String deadlineDescription = words[2];
            String deadlineBy = words[3];
            Deadline d = new Deadline(deadlineDescription, deadlineBy);
            if (isDone){
                d.markDone();
            }
            return d;
        case "E":
            String eventDescription = words[2];
            String eventFrom = words[3];
            String eventTo = words[4];
            Event e = new Event(eventDescription, eventFrom, eventTo);
            if (isDone){
                e.markDone();
            }
            return e;
        default:
            return null;
        }
    }

    /**
     * Saves all tasks in the given task list to the storage file.
     *
     * @param tasks the task list to be saved
     * @throws IOException if an error occurs while writing to the file
     */
    public void saveToFile(TaskList tasks) throws IOException {
        try (FileWriter fw = new FileWriter(filePath.toString())) {
            for (Task t : tasks.getTasks()) {
                fw.write(t.getFileText());
                fw.write(System.lineSeparator());
            }
        }
    }

}
