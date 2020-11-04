import java.io.*;
import java.nio.channels.FileLock;

public class ClientePar extends Cliente {
    
    public final static String fileName = "number.txt";

    public static void main(String[] args) throws IOException, InterruptedException {
        readFileData(fileName);
    }

    public static void readFileData(String fileName) throws IOException, InterruptedException {
        RandomAccessFile raf = null;
        FileLock fileLock = null;
        try{
            raf = new RandomAccessFile(fileName, "r");
            //fileLock = raf.getChannel().lock();
            int[] arrayIntNumbers = Cliente.readFile(fileName);
            boolean isEven = kindOfNumber(arrayIntNumbers);

            if (isEven) {
                System.out.println("Número par.");
                Cliente.showResult(arrayIntNumbers);
                Cliente.showAverage(arrayIntNumbers);
                Cliente.emptyFile(fileName);
            }
            LogFile.writeLogFile("Cliente par.");

            fileLock.release();
            fileLock = null;
            Thread.sleep(1000);
        } catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        } catch (NullPointerException npe){
            System.out.println("Fichero vacío.");
        }
        
        /*int iterationTimes = 0;
        while (iterationTimes <= 10) {
            iterationTimes++;
        }*/
    }

    private static boolean kindOfNumber(int[] arrayIntNumbers){
        try{
            if (arrayIntNumbers[0] % 2 == 0){
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Fichero vacio.");
        }
        return false;
    }

    
}
