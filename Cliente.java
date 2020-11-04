import java.io.*;
import java.nio.channels.FileLock;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.OverlappingFileLockException;
import java.util.stream.IntStream;

public class Cliente {

    public static final int bytesSize = 4;

    public static int[] readFile(String fileName) throws InterruptedException {
        FileLock fileLock = null;

        try {
            File dataFile = new File(fileName);
            RandomAccessFile randomAccessFile = new RandomAccessFile(dataFile, "rw");
            fileLock = randomAccessFile.getChannel().lock();

            int size = (int) (randomAccessFile.length() / bytesSize);
            int[] arrayIntNumbers = new int[size];
            int position = 0;

            boolean reading = true;
            while (reading) {
                randomAccessFile.seek(position * bytesSize);
                try {
                    int x = randomAccessFile.readInt();
                    arrayIntNumbers[position] = x;
                    position++;
                } catch (EOFException e) {
                    reading = false;
                }
            }
            fileLock.release();
            fileLock = null;
            randomAccessFile.close();
            Thread.sleep(1000);
            return arrayIntNumbers;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NonWritableChannelException e) {
            e.printStackTrace();
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
}