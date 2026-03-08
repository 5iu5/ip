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



public class StorageFile {

    private final Path filePath;

    public StorageFile(String filePathString) {
        this.filePath = Paths.get(filePathString);
    }

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

    public void saveToFile(TaskList tasks) throws IOException {
        try (FileWriter fw = new FileWriter(filePath.toString())) {
            for (Task t : tasks.getTasks()) {
                fw.write(t.getFileText());
                fw.write(System.lineSeparator());
            }
        }
    }

}
