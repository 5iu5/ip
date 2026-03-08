package yola.task;

/**
 * Represents a deadline task that has a description and a deadline.
 * <p>
 * A deadline task is a task that should be completed by a specified time.
 */
public class Deadline extends Task {
    /** The deadline associated with this task. */
    protected String by;

    /**
     * Creates a deadline task with the given description and deadline.
     *
     * @param description the description of the deadline task
     * @param by the deadline of the task
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the string representation of this deadline task.
     *
     * @return a string showing the deadline task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /**
     * Returns the text format for saving the deadline task into the storage file.
     *
     * @return a string representation to save the deadline task into file
     */
    @Override
    public String getFileText(){
        return "D" + " | " + super.getFileText() + " | " + by;
    }
}
