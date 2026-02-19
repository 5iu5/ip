public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public Task() {
        this.description = "";
        this.isDone = false;
    }


    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public String getDescription() {
        return this.description;
    }

    public void markDone() {
        isDone = true;
    }

    public void markUndone() {
        isDone = false;
    }


    public String toString() {
        // Returns the string representation of the task for printing
        return "[" + getStatusIcon() + "] " + description;
    }

    public String getFileText(){
        return (isDone? "1": "0") + " | "+ description;
    }


}