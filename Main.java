import java.io.File;
import java.util.Scanner;

public class Main {

    public ProcessBuilder pb = null;

    public static void main(String[] args) {
        Main main = new Main();
        String fileName = "number.txt";
        main.consoleMenu(fileName);
        System.out.println("Ok");
    }

    public void launchSuministrator(String fileName) {
        String className = "Suministrador";
        try {
            pb = new ProcessBuilder("java", className, fileName);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);

            Process proc = pb.start();
            System.out.println( "Job running" );
            proc.waitFor(); 
            System.out.println( "Job finished" );
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consoleMenu(String fileName) {
        Scanner s = new Scanner(System.in);
        System.out.println("1.- Lanzar suministrador");
        System.out.println("2.- Salir");

        int option = s.nextInt();
        switch (option) {
            case 1:
                launchSuministrator(fileName);
                break;
            case 2:

        }

    }

}
