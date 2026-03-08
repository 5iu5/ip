package yola.task;

/**
 * Represents a todo task with only a description.
 * <p>
 * A todo task does not have any associated date or time.
 */
public class Todo extends Task {

    /**
     * Creates a todo task with the given description.
     *
     * @param description the description of the todo task
     */
    public Todo(String description) {
        super(description);

    }

    /**
     * Returns the string representation of this todo task.
     *
     * @return a formatted string showing the todo task
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns the text format of this todo task for file storage.
     *
     * @return the file text representation of the todo task
     */
    @Override
    public String getFileText() {
        return "T" + " | " + super.getFileText();
    }
}
