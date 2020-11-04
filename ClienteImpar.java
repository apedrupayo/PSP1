import java.io.*;

public class ClienteImpar extends Cliente{
    
    public final static String fileName = "number.txt";

    public static void main(String[] args) throws FileNotFoundException {
        readFileData(fileName);
    }

    public static void readFileData(String fileName) throws FileNotFoundException {
        try{
            int[] arrayIntNumbers = Cliente.readFile(fileName);
            boolean isOdd = kindOfNumber(arrayIntNumbers);
            if (isOdd){
                System.out.println("Número impar.");
                Cliente.showResult(arrayIntNumbers);
                Cliente.showAverage(arrayIntNumbers);
                Cliente.emptyFile(fileName);
            }
            LogFile.writeLogFile("Cliente impar.");
        } catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        } catch (NullPointerException npe){
            System.out.println("Fichero vacío.");
        }
    }

    private static boolean kindOfNumber(int[] arrayIntNumbers) {
        try{
            if (arrayIntNumbers[0] % 2 != 0){
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Fichero vacio.");
        }
        return false;
    }
}
