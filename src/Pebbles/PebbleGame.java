package pebbles;
import java.util.LinkedList;
import java.util.Random;
import java.io.*;

/**
 * Created by Andrei on 10/20/2014.
 */
class PebbleGame {

    class Player {

        private int name;
        private LinkedList pebbles;
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
            int x = this.pebbles.size();

            for (int j=0; j<x; j++) {
                Pebble current = (Pebble) this.pebbles.get(j);
                total += current.getWeight();
            }
            return total;
        }

        int getName() {
            return this.name;
        }

        int getTotalPebbles() {
            return this.totalPebbles;
        }

        Pebble discardPebble() {

            this.totalPebbles--;
            Random random1 = new Random();
            int handIndex = random1.nextInt(9);
            Pebble discardedPebble = (Pebble)this.pebbles.remove(handIndex);
            return discardedPebble;

        }

        void takePebble(Bag bag) {

            this.totalPebbles++;
            Random random1 = new Random();
            int bagIndex = random1.nextInt(bag.sizeOfBag()-1);
            this.pebbles.add(bag.removePebble(bagIndex));

        }
    }

    static void main(String[] args) {

        Bag bagA = new Bag("A","white");
        Bag bagB = new Bag("B","white");
        Bag bagC = new Bag("C","white");

        Bag bagX = new Bag("X","black");
        Bag bagY = new Bag("Y","black");
        Bag bagZ = new Bag("Z","black");

        fillBag(bagX,args[0]);
        fillBag(bagX,args[1]);
        fillBag(bagX,args[2]);





    }

    static void fillBag(Bag bag, String fileName) {

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader in = new BufferedReader(fileReader);
            String values1 = in.readLine();
            String[] xWeights = values1.split(",");
            for (int i=0; i<xWeights.length; i++) {
                int weight;
                weight = Integer.parseInt(xWeights[i]);
                if (weight <= 0) {
                    System.out.println("Weight value is smaller than 0.");
                    break;
                }
                Pebble bagFiller = new Pebble(weight);
                bag.addPebble(bagFiller);
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("IO Exception thrown.");
            e.printStackTrace();
        }
    }
}
