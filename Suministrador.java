import java.io.File;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

public class Suministrador {

    public static int length = 0;

    public static void main(String[] args) {
        String fileName = args[0];
        writeFile(fileName);
    }

    public static void writeFile(String fileName) {
        RandomAccessFile raf = null;
        FileLock bloqueo = null;
        File file;
        int iterationTimes = 1;

        // Preparamos el acceso al fichero
        file = new File(fileName);
        while (iterationTimes <= 30) {// escribiremos 10 datos
            try {
                raf = new RandomAccessFile(file, "rwd"); // Abrimos el fichero
                // Sección crítica
                bloqueo = raf.getChannel().lock();
                System.out.println("Suministrador: Entra en la sección");
                if (raf.length() == 0) {
                    amountofNumbers();
                    int randomNumber;
                    for (int i = 0; i < length; i++) {
                        randomNumber = (int) (Math.random() * 20 + 1);
                        System.out.println("suministrador " + randomNumber);
                        raf.writeInt(randomNumber);
                    }
                    raf.close();
                    LogFile.writeLogFile("Suministrador.");
                } else {
                    System.out.println("Suministrador: no puede escribir");
                }
                System.out.println("Suministrador: Sale en la sección");
                bloqueo.release();
                bloqueo = null;
                // Fin sección crítica
                // *******************
                Thread.sleep(500); // Simulamos tiempo de creación del dato
            } catch (Exception e) {
                System.err.println("Suministrador. Error al acceder al fichero.");
                System.err.println(e.toString());
            }
            iterationTimes++;
        }
    }

    public static void amountofNumbers() {
        length = (int) (Math.random() * 5 + 1);// Amount of number to generate
    }
}