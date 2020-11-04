import java.io.*;
import java.nio.channels.FileLock;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.OverlappingFileLockException;

public class ClientePar extends Cliente {

    public final static String fileName = "number.txt";

    public static void main(String[] args) throws IOException, InterruptedException {
        readFileData(fileName);
    }

    public static void readFileData(String fileName) throws IOException, InterruptedException {
        RandomAccessFile raf = null;
        FileLock fileLock = null;

        int iterationTimes = 1;
        while (iterationTimes <= 50) {
            try {
                raf = new RandomAccessFile(fileName, "rw");
                //fileLock = raf.getChannel().lock();
                int[] arrayIntNumbers = Cliente.readFile(fileName);
                boolean isEven = kindOfNumber(arrayIntNumbers);

                if (isEven) {
                    System.out.println("***************");
                    System.out.println("Número par.");
                    Cliente.showResult(arrayIntNumbers);
                    System.out.println();
                    Cliente.showAverage(arrayIntNumbers);
                    System.out.println("***************");
                    Cliente.emptyFile(fileName);
                    LogFile.writeLogFile("Cliente par.");
                }
                //fileLock.release();
                fileLock = null;
                raf.close();
                Thread.sleep(3000);
            } catch (FileNotFoundException fnfe) {
                fnfe.printStackTrace();
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            } catch (NonWritableChannelException e) {
                e.printStackTrace();
            } catch (OverlappingFileLockException e){
                e.printStackTrace();
            }
            iterationTimes++;
        }

        /*
         * int iterationTimes = 0; while (iterationTimes <= 10) { iterationTimes++; }
         */
    }

    private static boolean kindOfNumber(int[] arrayIntNumbers) {
        try {
            if (arrayIntNumbers[0] % 2 == 0) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Esperando");
        }
        return false;
    }

}
