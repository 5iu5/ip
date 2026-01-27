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
        String[] textList = new String[100];
        int listSize = 0;

        while(true){
            line = in.nextLine();
            if (line.equals("bye")){
                break;
            }
            if (line.equals("list")){
                System.out.println("    ____________________________________________________________");
                for (int i = 0; i < listSize; i += 1){
                    System.out.println("     " + (i+1)+ ". " + textList[i]);
                }
                System.out.println("    ____________________________________________________________");
            }
            else{
                textList[listSize] = line;
                listSize += 1;
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
