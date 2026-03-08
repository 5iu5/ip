package yola.task;

/**
 * Represents a generic task with a description and completion status.
 * <p>
 * This is the parent class for specific task types such as
 * todo, deadline, and event.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a task with the given description.
     * The task is initially marked as not done.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Creates an empty task that is initially marked as not done.
     */
    public Task() {
        this.description = "";
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "X" if the task is done, otherwise " "
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the description of the task.
     *
     * @return the task description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Marks the task as done.
     */
    public void markDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markUndone() {
        isDone = false;
    }

    /**
     * Returns the string representation of the task for display.
     *
     * @return a formatted string showing the task status and description
     */
    public String toString() {
        // Returns the string representation of the task for printing
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns the text representation of the task for file storage.
     *
     * @return the file text representation of the task
     */
    public String getFileText() {
        return (isDone ? "1" : "0") + " | " + description;
    }


}