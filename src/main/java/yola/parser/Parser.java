package yola.parser;
import yola.command.Command;
import yola.command.DeadlineCommand;
import yola.command.DeleteCommand;
import yola.command.EventCommand;
import yola.command.ExitCommand;
import yola.command.FindCommand;
import yola.command.InvalidCommand;
import yola.command.ListCommand;
import yola.command.MarkCommand;
import yola.command.TodoCommand;
import yola.command.UnmarkCommand;

public class Parser {
    public static Command parse(String input){
        // Split user input into command word and command body (the remaining line)
        String[] commands = input.strip().split("\\s+", 2);
        String commandWord = commands[0];
        String commandBody = (commands.length > 1) ? commands[1] : "";

        switch (commandWord) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "find":
            return new FindCommand(commandBody);
        case "mark":
            return new MarkCommand(commandBody);
        case "unmark":
            return new UnmarkCommand(commandBody);
        case "delete":
            return new DeleteCommand(commandBody);
        case "todo":
            return new TodoCommand(commandBody);
        case "deadline":
            return new DeadlineCommand(commandBody);
        case "event":
            return new EventCommand(commandBody);
        default:
            return new InvalidCommand();
        }
    }
}
