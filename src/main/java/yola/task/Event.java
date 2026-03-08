package yola.task;

/**
 * Represents an event task that has a description, a start time,
 * and an end time.
 * <p>
 * An event task occurs over a period of time, from a specified
 * start time to a specified end time.
 */
public class Event extends Task {

    protected String from;

    protected String to;

    /**
     * Creates an event task with the given description, start time,
     * and end time.
     *
     * @param description the description of the event task
     * @param from the start time of the event
     * @param to the end time of the event
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the string representation of this event task.
     *
     * @return a formatted string showing the event task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    /**
     * Returns the text format for saving the event task into the storage file.
     *
     * @return a string representation to save the event task into file
     */
    @Override
    public String getFileText(){
        return "E" + " | " + super.getFileText() + " | " + from + " | " + to ;
    }
}
