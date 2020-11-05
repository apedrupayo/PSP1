import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileLock;

public class Suministrador {

    public static int length = 0;

    public static void main(String[] args) throws IOException, InterruptedException {
        String fileName = args[0];
        writeFile(fileName);
    }

    public static void writeFile(String fileName) throws IOException, InterruptedException {
        RandomAccessFile raf = null;
        FileLock fileLock = null;
        File file;
        int iterationTimes = 1;

        // Preparamos el acceso al fichero
        file = new File(fileName);
        while (iterationTimes <= 14) {// escribiremos 10 datos
            try {
                raf = new RandomAccessFile(file, "rwd"); // Abrimos el fichero
                // Sección crítica
                fileLock = raf.getChannel().lock();
                System.out.println("Suministrador: Entra en la sección");
                if (raf.length() == 0) {
                    amountofNumbers();
                    int randomNumber;
                    for (int i = 0; i < length; i++) {
                        randomNumber = (int) (Math.random() * 20 + 1);
                        System.out.println("Numero " + i + ": " + randomNumber);
                        raf.writeInt(randomNumber);
                    }
                } else {
                    System.out.println("Suministrador: esperando a ser leido.");
                }
                System.out.println("Suministrador: Sale en la sección");
                LogFile.writeLogFile("Suministrador.");
                fileLock.release();
                raf.close();
                fileLock = null;
                Thread.sleep(3000); // Simulamos tiempo de creación del dato
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
            iterationTimes++;
        }
    }

    public static void amountofNumbers() {
        length = (int) (Math.random() * 5 + 1);// Amount of number to generate
    }
}