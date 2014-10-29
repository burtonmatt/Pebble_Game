package pebbles;
import java.io.*;

/**
 * The WriteToFile Class is used to write the appropriate message to the
 * wright player's file.
 *
 */
class WriteToFile {

    /**
     * Write a line in the given player's file
     * @param i represents the player's number
     * @param message the message that we wish to write into the file
     */
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

    /**
     * This method creates the file for the given player number
     * @param i represents the player's number
     */
    static synchronized void create(int i) {
        try {
            String fileName = "player" + i + "_output.txt";
            new File(fileName);

        } catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * The method will close all the files created
     * @param i the total number of players
     */
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
