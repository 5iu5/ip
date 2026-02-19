public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    static public String getDeadline(String body) throws YolaException {
        if (!body.contains(" /by")) {
            throw new YolaException("Missing deadline!! Where is the deadline for your task?");
        }
        String[] parts = body.split(" /by", 2);
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new YolaException("Deadline is not filled!! Please input the deadline!!");
        }
        return parts[1];
    }

    static public String getDescription(String body) throws YolaException {
        if (body.isEmpty()) {
            throw new YolaException("Where is the description and deadline for your task??");
        }
        String[] parts = body.split(" /by", 2);
        if (body.contains(" /by")) {

            if (parts[0].isEmpty()) {
                throw new YolaException("Where is your task description???");
            }
        }
        return parts[0];
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String getFileText(){
        return "D" + " | " + super.getFileText() + " | " + by;
    }
}
