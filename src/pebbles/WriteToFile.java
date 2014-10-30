package pebbles;
import java.io.*;

/**
 * Created by Andrei on 10/26/2014.
 * Version v1.12
 */
class WriteToFile {


    static synchronized void write(int i,String message){
        try {
            String fileName = "player" + i + "_output.txt";
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(message);
        } catch(Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
    static synchronized void create(int i) {
        try {
            String fileName = "player" + i + "_output.txt";
            new File(fileName);

        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    static synchronized void closing(int i){
        for (int j=1;j<=i;j++){
            try {
                String fileName = "player" + i + "_output.txt";
                FileWriter fstream = new FileWriter(fileName);
                fstream.close();
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
