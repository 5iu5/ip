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

        while(true){
            line = in.nextLine();
            if (line.equals("bye")){
                break;
            }
            System.out.println("    ____________________________________________________________");
            System.out.print("    ");

            System.out.println(line);
            System.out.println("    ____________________________________________________________");
        }


        System.out.println("    ____________________________________________________________");
        System.out.println("    Bye. Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");

    }
}
