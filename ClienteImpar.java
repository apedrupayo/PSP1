import java.io.*;
import java.nio.channels.FileLock;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.OverlappingFileLockException;

public class ClienteImpar extends Cliente {

    public final static String fileName = "number.txt";

    public static void main(String[] args) throws IOException, InterruptedException {
        readFileData(fileName);
    }

    public static void readFileData(String fileName) throws IOException, InterruptedException {
        int iterationTimes = 1;
        while (iterationTimes <= 50) {
            try {
                int[] arrayIntNumbers = Cliente.readFile(fileName);
                boolean isOdd = kindOfNumber(arrayIntNumbers);
                if (isOdd) {
                    System.out.println("***************");
                    System.out.println("Número impar.");
                    Cliente.showResult(arrayIntNumbers);
                    System.out.println();
                    Cliente.showAverage(arrayIntNumbers);
                    System.out.println("***************");
                    Cliente.emptyFile(fileName);
                    LogFile.writeLogFile("Cliente impar.");
                }
                Thread.sleep(1000);
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            } catch (NonWritableChannelException e) {
                e.printStackTrace();
            } catch (OverlappingFileLockException e) {
                e.printStackTrace();
            }
            iterationTimes++;
        }
    }

    private static boolean kindOfNumber(int[] arrayIntNumbers) {
        try {
            if (arrayIntNumbers[0] % 2 != 0) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Esperando");
        }
        return false;
    }
}
