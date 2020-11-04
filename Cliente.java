import java.io.*;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.stream.IntStream;

public class Cliente {

    public static final int bytesSize = 4;

    public static int[] readFile(String fileName) throws InterruptedException {
        try {
            File dataFile = new File(fileName);
            RandomAccessFile randomAccessFile = new RandomAccessFile(dataFile, "r");

            int size = (int) (randomAccessFile.length() / bytesSize);
            int[] arrayIntNumbers = new int[size];
            int position = 0;

            boolean reading = true;
            while(reading){
                randomAccessFile.seek(position * bytesSize);
                try {
                    int x = randomAccessFile.readInt();
                    arrayIntNumbers[position] = x;
                    position++;
                } catch (EOFException e) {
                    reading = false;
                }
            }
            randomAccessFile.close();
            Thread.sleep(1000);
            return arrayIntNumbers;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    public static void showResult(int[] arrayIntNumbers) throws FileNotFoundException {
        for (int i = 0; i < arrayIntNumbers.length; i++) {
            System.out.print(arrayIntNumbers[i] + ", ");
        }
    }

    public static void showAverage(int[] arrayIntNumbers) throws FileNotFoundException {
        int sum = IntStream.of(arrayIntNumbers).sum();
        double average = (double) sum / arrayIntNumbers.length;
        System.out.println("Media aritmética: " + average);
    }

    public static void emptyFile(String fileName) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(fileName);
        printWriter.write("");
        printWriter.close();
    }

    public static int[] readFilePrueba(String fileName) {
        RandomAccessFile raf = null;
        FileLock fileLock = null;

        int iterationTimes = 1;
        int position = 0;
        int bytesSize = 4;
        //int readedNumber;
        int[] arrayIntNumbers;
        int size;

        // Preparamos el acceso al fichero
        File file = new File(fileName);
        while (iterationTimes <= 10) {// leeremos 10 datos
            try {
                // size = (int) (raf.length() / bytesSize);
                arrayIntNumbers = new int[5];
                raf = new RandomAccessFile(file, "rwd"); // Abrimos el fichero

                // ***************
                // Sección crítica
                fileLock = raf.getChannel().lock();
                System.out.println("Cliente: Entra en la sección");

                boolean reading = true;
                while (reading) {
                    raf.seek(position * bytesSize);
                    try {
                        arrayIntNumbers[position] = raf.readInt();
                        System.out.println(arrayIntNumbers[position]);
                        position++;
                    } catch (EOFException e) {
                        reading = false;
                    }
                }
                iterationTimes++;
                fileLock.release();
                fileLock = null;
                Thread.sleep(500);// simulamos tiempo de procesamiento del dato

                return arrayIntNumbers;
                // Fin sección crítica
                // *******************
            } catch (IOException e) {
                System.err.println("Cliente. Error al acceder al fichero.");
                System.err.println(e.toString());
            } catch (OverlappingFileLockException ex) {
                System.err.println("Cliente. Error en el bloqueo del fichero.");
                System.err.println(ex.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}