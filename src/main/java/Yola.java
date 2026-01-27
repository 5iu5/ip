import java.util.Scanner;

public class Yola {


    public static void main(String[] args) {

        String logo =    " __   __      _       \n"
                        + " \\ \\ / /___  | | __ _ \n"
                        + "  \\ V // _ \\ | |/ _` |\n"
                        + "   | || (_) || | (_| |\n"
                        + "   |_| \\___/ |_|\\__,_|\n";

        System.out.println("Hello from\n" + logo);
        System.out.println("    ____________________________________________________________");
        System.out.println("    Hello! I'm Yola");
        System.out.println("    What can I do for you?");
        System.out.println("    ____________________________________________________________");

        String line;
        Scanner in = new Scanner(System.in);
        Task[] taskList = new Task[100];
        int size = 0;

        while(true){
            line = in.nextLine();
            String[] words = line.strip().split("\\s+");
            if (line.equals("bye")){
                break;
            }

            if (line.equals("list")){
                System.out.println("    ____________________________________________________________");
                System.out.println("    Here are the tasks in your list:");
                for (int i = 0; i < size; i += 1){
                    System.out.println("    " + (i+1)+ ".["+ taskList[i].getStatusIcon() + "] " + taskList[i].description);
                }
                System.out.println("    ____________________________________________________________");
            }

            else if((words[0].equals("mark") || words[0].equals("unmark")) && words.length == 2){
                //Check if it is a mark/unmark command


                try {
                     int taskNum = Integer.parseInt(words[1]);
                     if (taskNum < 1 || taskNum > size) {
                         System.out.println("invalid task number");
                         continue;
                     }
                     Task t = taskList[taskNum-1];
                    System.out.println("    ____________________________________________________________");

                    if (words[0].equals("mark")){
                         //mark task
                         t.markDone();
                         System.out.println("    Nice! I've marked this task as done:");
                    }
                     else {
                         //unmark task
                         t.unmark();
                         System.out.println("        OK, I've marked this task as not done yet:");
                     }
                     System.out.println("      " + "[" + t.getStatusIcon() + "] " + t.description);
                     System.out.println("    ____________________________________________________________");

                }catch (NumberFormatException e) {
                    System.out.println("Please enter a valid task number.");
                }
            }
            else{
                Task t = new Task(line);
                taskList[size] = t;
                size += 1;
                System.out.println("    ____________________________________________________________");
                System.out.println("     added: " + line);
                System.out.println("    ____________________________________________________________");
            }

        }


        System.out.println("    ____________________________________________________________");
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");

    }
}
