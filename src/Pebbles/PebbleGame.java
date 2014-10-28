package pebbles;
import java.util.LinkedList;
import java.util.Random;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by Andrei on 10/20/2014.
 *
 *
 */
class PebbleGame {

    static class Player implements Runnable {

        private final int name;
        private LinkedList<Pebble> pebbles;
        Random next = new Random();

        public Player (int name) {
            this.name = name;
            this.pebbles = new LinkedList<Pebble>();

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

        synchronized void discardPebble() {

            Random random1 = new Random();
            int handIndex = random1.nextInt(9);
            Pebble discarding = this.pebbles.remove(handIndex);
            Bag parent = this.pebbles.getLast().getParentBag();
            if (parent == bagX) {
                bagA.addPebble(discarding);
                String message = "Player"+this.getPlayerName()+" has discarded a"+discarding.getWeight()+" to bagA";
                WriteToFile.write(this.getPlayerName(), message);
                System.out.println(message);
            }
            if (parent == bagY) {
                bagB.addPebble(discarding);
                String message = "Player"+this.getPlayerName()+" has discarded a"+discarding.getWeight()+" to bagB";
                WriteToFile.write(this.getPlayerName(), message);
                System.out.println(message);
            }
            if (parent == bagZ) {
                bagC.addPebble(discarding);
                String message = "Player"+this.getPlayerName()+" has discarded a"+discarding.getWeight()+" to bagC";
                WriteToFile.write(this.getPlayerName(), message);
                System.out.println(message);
            }
        }

        synchronized void takeInitialPebble(Bag bag) {


            Random random1 = new Random();
            int bagIndex = random1.nextInt(bag.sizeOfBag()-1);
            this.pebbles.add(bag.removePebble(bagIndex));

        }

        synchronized void takePebble() {
            String message;
            int randomBag = next.nextInt(2);

            if (randomBag == 0){
                if (bagX.sizeOfBag() !=0) {
                    takeInitialPebble(bagX);
                    message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagX.getName();
                    WriteToFile.write(name,message);
                    System.out.println(message);
                }
                else {
                    if (bagY.sizeOfBag() !=0){
                        takeInitialPebble(bagY);
                        message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagY.getName();
                        WriteToFile.write(name,message);
                        System.out.println(message);
                    }
                    else {
                        if (bagZ.sizeOfBag() != 0){
                            takeInitialPebble(bagZ);
                            message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagZ.getName();
                            WriteToFile.write(name,message);
                            System.out.println(message);
                        }
                        else {
                            System.out.println("All bags are empty!");

                        }
                    }
                }
            }
            if (randomBag == 1){
                if (bagY.sizeOfBag() !=0) {
                    takeInitialPebble(bagY);
                    message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagY.getName();
                    WriteToFile.write(name,message);
                    System.out.println(message);
                }
                else {
                    if (bagZ.sizeOfBag() !=0){
                        takeInitialPebble(bagZ);
                        message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagZ.getName();
                        WriteToFile.write(name,message);
                        System.out.println(message);
                    }
                    else {
                        if (bagX.sizeOfBag() != 0){
                            takeInitialPebble(bagX);
                            message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagX.getName();
                            WriteToFile.write(name,message);
                            System.out.println(message);
                        }
                        else {
                            System.out.println("All bags are empty!");

                        }
                    }
                }
            }
            if (randomBag == 2){
                if (bagZ.sizeOfBag() !=0) {
                    takeInitialPebble(bagZ);
                    message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagZ.getName();
                    WriteToFile.write(name,message);
                    System.out.println(message);
                }
                else {
                    if (bagX.sizeOfBag() !=0){
                        takeInitialPebble(bagX);
                        message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagX.getName();
                        WriteToFile.write(name,message);
                        System.out.println(message);
                    }
                    else {
                        if (bagY.sizeOfBag() != 0){
                            takeInitialPebble(bagY);
                            message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagY.getName();
                            WriteToFile.write(name,message);
                            System.out.println(message);
                        }
                        else {
                            System.out.println("All bags are empty!");

                        }
                    }
                }
            }

        }

        public void run() {
            WriteToFile.create(name);
            Bag full;
            String message;
            for (int i =0;i< 9;i++) {
                full = PebbleGame.fullestBag(bagX,bagY,bagZ);
                takeInitialPebble(full);
                message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+full.getName();
                WriteToFile.write(name,message);
                System.out.println(message);
            }

            while (!PebbleGame.done) {
                if (bagX.sizeOfBag() == 0 && bagA.sizeOfBag() != 0){
                    bagX.transferPebbles(bagA);
                }
                if (bagY.sizeOfBag() == 0 && bagB.sizeOfBag() != 0){
                    bagY.transferPebbles(bagB);
                }
                if (bagZ.sizeOfBag() == 0 && bagC.sizeOfBag() != 0){
                    bagZ.transferPebbles(bagC);
                }

                takePebble();


                if (this.getTotalValue() == 100) {
                    PebbleGame.done = true;
                    endCredits(this.getPlayerName());
                }

                discardPebble();
            }

        }

        private synchronized void endCredits(int i){
            String message;
            if (this.getTotalValue() == 100) {
                PebbleGame.done = true;
                message = "player"+i+" has won";
                WriteToFile.write(i,message);
                System.out.println(message);

            }
            for (int j=1;j<=PebbleGame.playersNr.get(); j++) {
                message = "player"+j+" has lost, as player"+i+" has won.";
                if (j != i) {
                    WriteToFile.write(j,message);
                }

            }
        }
    }

    private static Bag fullest;

    static Bag bagA = new Bag("A","white");
    static Bag bagB = new Bag("B","white");
    static Bag bagC = new Bag("C","white");

    static Bag bagX = new Bag("X","black");
    static Bag bagY = new Bag("Y","black");
    static Bag bagZ = new Bag("Z","black");

    static boolean done = false;
    static AtomicInteger playersNr;

    public static void main(String args[]) throws NotEnoughPebblesException, InvalidPlayersNumberException {

        fillBag(bagX,args[0]);
        fillBag(bagY,args[1]);
        fillBag(bagZ,args[2]);
        playersNr = new AtomicInteger(Integer.parseInt(args[3]));
        checkPlayersNumber(playersNr.get());
        checkEnoughPebbles(bagX, bagY,bagZ, playersNr.get());

        int i;

        for (i = 1; i <= playersNr.get(); i++) {
            new Thread(new Player(i)).start();

        }




        WriteToFile.closing(playersNr.get());
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
                bagFiller.setParentBag(bag);
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

    private static Bag fullestBag(Bag bagx, Bag bagy, Bag bagz) {

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