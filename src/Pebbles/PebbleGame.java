package pebbles;
import java.util.LinkedList;
import java.util.Random;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by Andrei on 10/20/2014.
 */
class PebbleGame {

    static class Player implements Runnable {

        private final int name;
        private LinkedList<Pebble> pebbles;
        private int totalValue;
        private int totalPebbles;

        Player (int name) {
            this.name = name;
            this.pebbles = new LinkedList<Pebble>();
            this.totalValue = 0;
            this.totalPebbles = 0;
        }



        int getTotalValue() {
            int total = 0;

            for (Object pebble : this.pebbles) {
                Pebble current = (Pebble) pebble;
                total += current.getWeight();
            }
            return total;
        }

        int getPlayerName() {
            return this.name;
        }

        int getTotalPebbles() {
            return this.totalPebbles;
        }

        Pebble discardPebble() {

            this.totalPebbles--;
            Random random1 = new Random();
            int handIndex = random1.nextInt(9);
            return (Pebble)this.pebbles.remove(handIndex);

        }

        void takePebble(Bag bag) {

            this.totalPebbles++;
            Random random1 = new Random();
            int bagIndex = random1.nextInt(bag.sizeOfBag()-1);
            this.pebbles.add(bag.removePebble(bagIndex));

        }

        public void run() {

        }
    }

    private static Bag fullest;
    private static LinkedList<Player> playersList;

    public static void main(String args[]) throws NotEnoughPebblesException, InvalidPlayersNumberException {

        Bag bagA = new Bag("A","white");
        Bag bagB = new Bag("B","white");
        Bag bagC = new Bag("C","white");

        Bag bagX = new Bag("X","black");
        Bag bagY = new Bag("Y","black");
        Bag bagZ = new Bag("Z","black");

        fillBag(bagX,args[0]);
        fillBag(bagY,args[1]);
        fillBag(bagZ,args[2]);
        AtomicInteger playersNr = new AtomicInteger(Integer.parseInt(args[3]));
        checkPlayersNumber(playersNr.get());
        checkEnoughPebbles(bagX, bagY,bagZ, playersNr.get());

    }

    private static void fillBag(Bag bag, String fileName) {

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader in = new BufferedReader(fileReader);
            String values1 = in.readLine();
            String[] xWeights = values1.split(",");
            for (String xWeight : xWeights) {
                int weight;
                weight = Integer.parseInt(xWeight);
                if (weight <= 0) {
                    System.out.println("Weight value is smaller than 0.");
                    break;
                }
                Pebble bagFiller = new Pebble(weight);
                bag.addPebble(bagFiller);
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("File not found for Bag: "+bag.getName());

        }
        catch (IOException e) {
            System.out.println("IO Exception thrown.");

        }
    }

    private static void checkEnoughPebbles(Bag bagx, Bag bagy, Bag bagz, int players) throws NotEnoughPebblesException {
        int counter;
        counter = bagx.sizeOfBag()+bagy.sizeOfBag()+bagz.sizeOfBag();
        if (counter < ((players*9)+1)) {
            throw new NotEnoughPebblesException();
        }
    }

    private static void checkPlayersNumber(int players) throws InvalidPlayersNumberException {
        if (players < 2)
            throw new InvalidPlayersNumberException();

    }

    private Bag fullestBag(Bag bagx, Bag bagy, Bag bagz) {

        int max = 0;
        if (max < bagx.sizeOfBag()) {
            fullest = bagx;
            max = bagx.sizeOfBag();
        }
        if (max < bagy.sizeOfBag()) {
            fullest = bagy;
            max = bagy.sizeOfBag();
        }
        if (max < bagz.sizeOfBag()) {
            fullest = bagz;
        }
        return fullest;
    }


}